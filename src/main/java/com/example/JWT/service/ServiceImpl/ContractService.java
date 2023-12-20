package com.example.JWT.service.ServiceImpl;

import com.example.JWT.dto.Request.ContractRequest;
import com.example.JWT.model.Entity.Bedsitter;
import com.example.JWT.model.Entity.Contract;
import com.example.JWT.model.Entity.User;
import com.example.JWT.repository.BedsitterRepository;
import com.example.JWT.repository.ContractRepository;
import com.example.JWT.repository.userRepository;
import com.example.JWT.service.Interface.IContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ContractService implements IContractService {

    @Autowired
    private final ContractRepository contractRepository;

    @Autowired
    private final userRepository userRepository;

    @Autowired
    private final BedsitterRepository bedsitterRepository;

    @Autowired
    private final UserService userService;

    @Autowired
    private final AuthenticationService authenticationService;

    @Override
    public Page<Contract> getUserBookingHistory(int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()){
            return null;
        }
        Integer userId = ((User) authentication.getPrincipal()).getId();
        if(userId != null){
            Pageable pageable = PageRequest.of(page,size);
           Page<Contract> contracts =  contractRepository.findByTenantId(userId, pageable);
            return contractRepository.findByTenantId(userId, pageable);
        }
        return Page.empty();
    }

    @Override
    public void createContract(ContractRequest request) {
        Contract contract = new Contract();

        User tenant = userRepository.findById(request.getTenant_id()).orElseThrow(()->new RuntimeException("User not found !"));
        contract.setTenant(tenant);

        Bedsitter bedsitter = bedsitterRepository.findById(request.getBedsitter_id()).orElseThrow(()->new RuntimeException("Bedsitter not found !"));
        contract.setBedsitter(bedsitter);

        contract.setEffectiveFrom(request.getEffectiveFrom());
        contract.setTimeForRent(request.getTimeForRent());
        contract.setEffectiveTo(request.getEffectiveTo());
        contract.setContractValue(request.getContractValue());
        contract.setCreatedAt(request.getCreatedAt());
        contract.setStatus("Pending");

        contractRepository.save(contract);

        scheduleTask(()->{
                Contract savedContract = contractRepository.findById(contract.getContractId()).orElse(null);
                if(savedContract != null && "Pending".equals(savedContract.getStatus())){
                    savedContract.setStatus("Active");
                    contractRepository.save(savedContract);
                }
        }, 60 * 60 * 60 * 1000);
    }

    private void scheduleTask(Runnable task, long delay){
        Executors.newSingleThreadScheduledExecutor().schedule(task, delay, TimeUnit.MILLISECONDS);
    }

    public void cancelContract(Long contractId) {
        Optional<Contract> optionalContract = contractRepository.findById(contractId);
        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();

            // Kiểm tra xem hợp đồng vẫn trong thời gian hủy (Pending) và chưa quá 60  giờ kể từ ngày tạo
            if ("Pending".equals(contract.getStatus()) && isWithinCancellationPeriod(contract.getCreatedAt())) {
                contract.setStatus("Cancel");
                contractRepository.save(contract);
            } else {
                throw new RuntimeException("Không thể hủy hợp đồng sau khoảng thời gian 12 giờ");
            }
        } else {
            throw new RuntimeException("Không tìm thấy hợp đồng");
        }
    }
    private boolean isWithinCancellationPeriod(Date createdDate) {
        // Kiểm tra xem thời gian hiện tại có nằm trong khoảng 60h kể từ createdDate không
        long currentTime = System.currentTimeMillis();
        long createdTime = createdDate.getTime();
        long elapsedTime = currentTime - createdTime;
        // Trong 12h là 1 * 60 * 1000 milliseconds
        return elapsedTime <= 60 * 60 * 60 * 1000;
    }

}


