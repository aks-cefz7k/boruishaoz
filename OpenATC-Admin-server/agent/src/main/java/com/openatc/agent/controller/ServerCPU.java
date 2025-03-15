package com.openatc.agent.controller;

import com.sun.management.OperatingSystemMXBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 * @author laoxia
 * @Date 2021/10/11 9:30
 * @Describetion 获取服务器性能信息
 */
@RestController
@RequestMapping("/info/")
public class ServerCPU {

    /**
     * @param
     * @return
     * @descripation 获取服务器内存信息
     * @Date 2021/10/11 16:59
     **/
    @GetMapping("memory")
    public void GetMemoryInfo() {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("Total RAM：" + mem.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024 + "GB");
        System.out.println("Available　RAM：" + mem.getFreePhysicalMemorySize() / 1024 / 1024 / 1024 + "GB");
    }

    /**
     * @param
     * @return
     * @throws
     * @descripation 获取服务器CPU信息
     * @Date 2021/10/11 16:59
     **/
    @GetMapping("cpu")
    public void GetCPUInfo() {
        OperatingSystemMXBean mem = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double processCpuLoad = mem.getProcessCpuLoad();
        double systemCpuLoad = mem.getSystemCpuLoad();
        long processCpuTime = mem.getProcessCpuTime();
        System.out.println("cpu1:" + processCpuLoad);
        System.out.println("cpu2:" + systemCpuLoad);
        System.out.println("cpu3:" + processCpuTime);
    }

    /**
     * @param
     * @return
     * @throws
     * @descripation 获取服务器磁盘信息
     * @Date 2021/10/11 16:58
     **/
    @GetMapping("disk")
    public void GetDiskInfo() {
        File[] disks = File.listRoots();
        for (File file : disks) {
            System.out.print(file.getPath() + "    ");
            long free = file.getUsableSpace() / 1024 / 1024 / 1024;
            long total = file.getTotalSpace() / 1024 / 1024 / 1024;
            System.out.print("空闲未使用 = " + free + "G" + "    ");
            System.out.print("已经使用 = " + (total - free) + "G" + "    ");
            System.out.print("总容量 = " + total + "G" + "    ");
            System.out.println();
        }
    }

}
