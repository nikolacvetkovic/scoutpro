package xyz.riocode.scoutpro.service;

import org.springframework.stereotype.Service;
import xyz.riocode.scoutpro.model.PsmlTransfer;
import xyz.riocode.scoutpro.repository.PsmlTransferRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PsmlTransferServiceImpl implements PsmlTransferService{

    private final PsmlTransferRepository psmlTransferRepository;

    public PsmlTransferServiceImpl(PsmlTransferRepository psmlTransferRepository) {
        this.psmlTransferRepository = psmlTransferRepository;
    }

    @Override
    public List<PsmlTransfer> getInLastMonthByUser(String username) {
        LocalDate date = LocalDate.now().minusMonths(1);
        return psmlTransferRepository.findByDateOfTransferAndUser(date, username);
    }
}
