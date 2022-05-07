package xyz.riocode.scoutpro.service;

import org.springframework.stereotype.Component;
import xyz.riocode.scoutpro.model.Transfer;
import xyz.riocode.scoutpro.repository.TransferRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;

    public TransferServiceImpl(TransferRepository transferRepository) {
        this.transferRepository = transferRepository;
    }

    @Override
    public List<Transfer> getInLastMonthByUser(String username) {
        LocalDate date = LocalDate.now().minusMonths(1);
        return transferRepository.findByDateOfTransferAndUser(date, username);
    }
}
