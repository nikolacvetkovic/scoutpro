package xyz.riocode.scoutpro.service;

import xyz.riocode.scoutpro.model.Transfer;

import java.util.List;

public interface TransferService {
    List<Transfer> getInLastMonthByUser(String username);
}
