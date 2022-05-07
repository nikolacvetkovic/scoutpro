package xyz.riocode.scoutpro.service;

import xyz.riocode.scoutpro.model.PsmlTransfer;

import java.util.List;

public interface PsmlTransferService {
    List<PsmlTransfer> getInLastMonthByUser(String username);
}
