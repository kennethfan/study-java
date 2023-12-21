package io.github.kennethfan.oshi;

import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;

import java.util.List;

/**
 * Created by kenneth on 2023/8/8.
 */
@Slf4j
public class OshiMain {

    public static void main(String[] args) {

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        CentralProcessor cpu = hal.getProcessor();
        log.info("cpu {}", cpu);

        GlobalMemory memory = hal.getMemory();
        log.info("memory {}", memory);

        List<HWDiskStore> hwDiskStoreList = hal.getDiskStores();
        log.info("hwDiskStores {}", hwDiskStoreList);
    }
}
