import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyProcess {
    // 正在运行的程序链表
    private static List<String> runningProcessesList = new ArrayList<>();

    // 已结束的程序链表
    private static List<String> endedProcessesList = new ArrayList<>();

    public static void main(String[] args) {
        // 设置控制台编码为UTF-8
        setConsoleEncoding("UTF-8");

        // 获取正在运行的进程信息，并将其添加到链表中
        System.out.println("---正在运行的进程---");
        List<ProcessHandle> runningProcesses = ProcessHandle.allProcesses()
                .collect(Collectors.toList());

        for (ProcessHandle process : runningProcesses) {
            String processInfo = process.pid() + " " + process.info().command().orElse("") + " "
                    + getProcessDuration(process) + " " + getProcessMemoryUsage(process);
            runningProcessesList.add(processInfo);
            System.out.println(processInfo);
        }

        // 对正在运行的进程信息进行排序
        sortRunningProcesses();

        System.out.println("---正在运行的进程（按进程ID从小到大排序）---");
        for (String processInfo : runningProcessesList) {
            System.out.println(processInfo);
        }

        // 获取已结束的进程信息，并将其添加到链表中
        System.out.println("---已结束的进程---");
        ProcessHandle.allProcesses()
                .filter(ProcessHandle::isAlive)
                .forEach(MyProcess::addEndedProcessInfo);

        // 对已结束的进程信息进行排序
        sortEndedProcesses();

        System.out.println("---已结束的进程（按进程ID从小到大排序）---");
        for (String processInfo : endedProcessesList) {
            System.out.println(processInfo);
        }
    }

    private static void addEndedProcessInfo(ProcessHandle process) {
        Optional<Instant> startInstant = process.info().startInstant();
        Optional<Duration> totalCpuDuration = process.info().totalCpuDuration();
        if (startInstant.isPresent() && totalCpuDuration.isPresent()) {
            Instant endTime = startInstant.get().plus(totalCpuDuration.get());
            Duration duration = Duration.between(startInstant.get(), endTime);
            String processInfo = process.pid() + " " + endTime + " " + duration;
            endedProcessesList.add(processInfo);
            System.out.println(processInfo);
        }
    }

    private static void printProcessEndTimeAndDuration(ProcessHandle process) {
        Optional<Instant> startInstant = process.info().startInstant();
        Optional<Duration> totalCpuDuration = process.info().totalCpuDuration();
        if (startInstant.isPresent() && totalCpuDuration.isPresent()) {
            Instant endTime = startInstant.get().plus(totalCpuDuration.get());
            Duration duration = Duration.between(startInstant.get(), endTime);
            System.out.println(process.pid()+"          "+endTime+"          "+duration);
        }
    }

    private static String getProcessDuration(ProcessHandle process) {
        long now = System.currentTimeMillis();
        long startTime = process.info().startInstant().orElse(Instant.now()).toEpochMilli();
        long duration = now - startTime;
        return duration + " 毫秒";
    }

    private static void setConsoleEncoding(String encoding) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "chcp", encoding);
            java.lang.Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getProcessMemoryUsage(ProcessHandle process) {
        String memoryUsage = "未知";
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("tasklist");
            processBuilder.redirectErrorStream(true);
            java.lang.Process tasklistProcess = processBuilder.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(tasklistProcess.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(String.valueOf(process.pid()))) {
                    String[] columns = line.trim().split("\\s+");
                    if (columns.length >= 5) {
                        memoryUsage = columns[4] + " K";
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memoryUsage;
    }

    private static void sortRunningProcesses() {
        Collections.sort(runningProcessesList, new ProcessInfoComparator
                () {
            @Override
            public int compare(String o1, String o2) {
                String[] info1 = o1.split(" ");
                String[] info2 = o2.split(" ");

                if (info1.length < 1 || info2.length < 1) {
                    return 0; // 返回0表示无法比较，保持原顺序
                }

                int pid1 = Integer.parseInt(info1[0]);
                int pid2 = Integer.parseInt(info2[0]);

                if (pid1 == pid2) {
                    return 0;
                } else if (pid1 == 0 || pid2 == 0) {
                    return pid1 == 0 ? 1 : -1; // 未知的进程放在最后
                } else {
                    return Integer.compare(pid1, pid2);
                }
            }
        });
    }

    private static void sortEndedProcesses() {
        Collections.sort(endedProcessesList, new ProcessInfoComparator() {
            @Override
            public int compare(String o1, String o2) {
                String[] info1 = o1.split(" ");
                String[] info2 = o2.split(" ");

                if (info1.length < 1 || info2.length < 1) {
                    return 0; // 返回0表示无法比较，保持原顺序
                }

                int pid1 = Integer.parseInt(info1[0]);
                int pid2 = Integer.parseInt(info2[0]);

                if (pid1 == pid2) {
                    return 0;
                } else if (pid1 == 0 || pid2 == 0) {
                    return pid1 == 0 ? 1 : -1; // 未知的进程放在最后
                } else {
                    return Integer.compare(pid1, pid2);
                }
            }
        });
    }

    // 自定义比较器
    private static abstract class ProcessInfoComparator implements Comparator<String> {
        @Override
        public abstract int compare(String o1, String o2);
    }

}
