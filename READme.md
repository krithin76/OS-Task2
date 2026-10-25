Dining Philosophers Using Monitor (Java) — “The Living Table”

Files: "DiningPhilosophersMonitor.java", "dining_philosophers_monitor.html", "README.md"

Build and Run

Requirements: JDK 17 or later recommended.

Compile the Java program:

javac DiningPhilosophersMonitor.java

Run the program:

java DiningPhilosophersMonitor

Optional arguments can be supported for the number of rounds, HTML output filename, and random seed.

Example:

java DiningPhilosophersMonitor 5 out.html 42

After execution, open "dining_philosophers_monitor.html" in a browser. The HTML file can replay the recorded execution without requiring a web server or internet connection, provided the CSS and JavaScript are embedded.

Algorithm

The program implements the Dining Philosophers Problem using Java's intrinsic monitor mechanism with "synchronized", "wait()", and "notifyAll()".

There are four philosophers and four shared forks. Each philosopher alternates between thinking, becoming hungry, and eating.

Each philosopher follows these steps:

1. Think for a short duration.
2. Enter the monitor and declare the philosopher hungry.
3. Check whether both neighboring forks are available.
4. If either fork is unavailable, wait until notified.
5. When both forks are available, change the state to "EATING".
6. Eat outside the synchronized block.
7. Enter the monitor again and change the state to "THINKING".
8. Notify waiting philosophers that the state has changed.
9. Repeat for the specified number of rounds.

The monitor maintains the shared philosopher states:

- "THINKING" — the philosopher is not requesting forks.
- "HUNGRY" — the philosopher is waiting for both forks.
- "EATING" — the philosopher has permission to eat.

Monitor Implementation

A single monitor object protects all shared philosopher states. Java's "synchronized" keyword ensures mutual exclusion while the monitor's methods execute.

The "wait()" method releases the monitor lock and suspends the current thread until notification or another permitted wake-up. The thread must recheck the condition after waking, so "wait()" is used inside a "while" loop.

The "notifyAll()" method wakes threads waiting on the monitor. Each awakened philosopher checks whether both neighboring forks are available before proceeding.

Unlike the semaphore implementation, this version does not use separate semaphore objects for the forks or a room semaphore. Fork availability is controlled by the shared state array and the monitor's condition checks.

Deadlock Prevention

A philosopher can begin eating only when both neighboring philosophers are not eating. The monitor protects this decision and the corresponding state change as one synchronized operation.

This prevents adjacent philosophers from eating simultaneously and avoids the circular-wait deadlock of the classic problem, assuming the state checks and transitions are implemented correctly.

Starvation

A basic monitor implementation does not necessarily guarantee starvation freedom. A hungry philosopher might repeatedly lose access to its forks because Java's intrinsic monitor does not promise FIFO scheduling.

A fair monitor can be designed using an explicit waiting queue or ticket mechanism to give philosophers a more orderly chance to eat.

Event Recording and Validation

The program can record the following events:

"THINK", "HUNGRY", "WAIT", "EAT_START", "EAT_END", "NOTIFY", and "DONE".

A thread-safe event recorder assigns sequence numbers to events and stores them for replay in the generated HTML file.

The validator checks:

1. Exactly four philosophers and four forks are represented.
2. Adjacent philosophers never eat simultaneously.
3. A philosopher starts eating only after receiving permission from the monitor.
4. All philosophers finish the specified number of rounds.
5. All philosophers eventually reach "DONE".
6. State transitions follow the legal order.
7. No philosopher remains marked as eating after its eating phase finishes.
8. Recorded events are consistent with the monitor's shared state.

Animation Controls

The HTML simulation can include:

- Play and pause.
- Reset.
- Step forward and backward.
- Playback speed control.
- Timeline slider for seeking.
- Clickable event timeline.
- Highlighting for the active philosopher.
- Visual states for thinking, hungry, waiting, and eating.
- A central panel showing shared monitor states and waiting philosophers.

The HTML file replays the recorded Java execution rather than controlling the live Java threads.

Viva Questions

1. What is a monitor in operating systems?
2. How is a Java monitor implemented using "synchronized"?
3. What is the purpose of "wait()" and "notifyAll()"?
4. Why should "wait()" be called inside a "while" loop?
5. How does a monitor prevent two adjacent philosophers from eating simultaneously?
6. What is the difference between semaphore and monitor implementations?
7. Can starvation occur in a monitor-based solution?
8. Why is mutual exclusion necessary?
9. Why should eating occur outside the synchronized block?
10. What is the purpose of event recording and trace validation?

References

- Oracle Java documentation: "Object.wait()" and "Object.notifyAll()".
- Oracle Java documentation: synchronized methods and statements.
- Silberschatz, Galvin and Gagne, Operating System Concepts, Dining Philosophers Problem.
- Tanenbaum, Modern Operating Systems, process synchronization.
