Dining Philosophers with Semaphores (Java) — “The Living Table”

Files: "DiningPhilosophers.java", "dining_philosophers_semaphore.html", "README.md"

Build and Run

Requirements: JDK 17 or later recommended.

Compile the Java program:

javac DiningPhilosophers.java

Run the program:

java DiningPhilosophers

Optional arguments can be implemented for the number of rounds, HTML output filename, and random seed.

Example:

java DiningPhilosophers 5 out.html 42

After execution, open "dining_philosophers_semaphore.html" in any modern browser. The HTML file can replay the recorded execution without a server or internet connection, provided all required JavaScript and CSS are embedded in the file.

Algorithm

The program uses four philosopher threads, four fork semaphores, and one room semaphore.

- Fork semaphores: Four "java.util.concurrent.Semaphore" objects, each initialized to 1.
- Room semaphore: One "Semaphore" initialized to 3.
- Philosophers: Four independent Java threads.

Each philosopher repeatedly follows these steps:

Think → Become hungry → Acquire room permit → Acquire left fork → Acquire right fork → Eat → Release both forks → Release room permit.

The left fork of philosopher "i" is "(i + 3) % 4", and the right fork is "i".

Why Semaphores Are Used

A binary-style semaphore initialized to 1 ensures that only one philosopher can hold a particular fork at a time. Java's "Semaphore" class supports this behavior, although it is not a distinct binary-semaphore type.

The room semaphore allows at most three philosophers to compete for forks simultaneously. This prevents the circular-wait situation in which all four philosophers hold one fork and wait indefinitely for another.

With at most three philosophers competing, at least one fork remains available, allowing the system to make progress.

Starvation

The algorithm prevents deadlock, but it does not automatically guarantee starvation freedom. Java semaphore fairness can be enabled using "new Semaphore(permits, true)", which provides FIFO-style fairness for queued acquisition requests. However, fairness of individual semaphores does not necessarily guarantee that every philosopher completes every round under all scheduling conditions.

Event Recording and Validation

The program can record events such as:

"THINK", "HUNGRY", "ROOM_TRY", "ROOM_OK", "FORK_REQ", "FORK_OK", "EAT_START", "EAT_END", "FORK_REL", "ROOM_REL", and "DONE".

A synchronized recorder or a suitable locking mechanism can assign sequence numbers and store events safely across threads. The generated HTML can replay these events in chronological order.

The validator should check:

1. Exactly four philosophers and four forks exist.
2. No fork is held by two philosophers simultaneously.
3. Adjacent philosophers never eat simultaneously.
4. A philosopher eats only while holding both required forks.
5. Every acquired fork and room permit is eventually released.
6. The room occupancy never exceeds three.
7. All four philosophers reach the "DONE" state.
8. Each philosopher follows the correct state-transition sequence.

Validation results should be displayed in both the Java console and the HTML interface.

Animation Controls

The HTML simulation can provide:

- Play and pause.
- Reset and step forward/backward.
- Adjustable playback speed.
- Timeline slider for seeking.
- Clickable timeline events.
- Keyboard controls using Space and the arrow keys.
- Visual indicators for the active philosopher, held forks, pending requests, and available room permits.

Viva Questions

1. Why are semaphores used for forks and the room?
2. Why is the room capacity set to three?
3. Which Coffman condition does the algorithm prevent?
4. Can deadlock occur if all four philosophers enter the room?
5. What is the difference between binary-style and counting semaphores?
6. Can starvation occur even when deadlock is prevented?
7. Why is synchronization required when recording events?
8. How does the HTML replay represent the Java thread execution?
9. What is the purpose of the validation checks?
10. What are the limitations of the simulation?

References

- Oracle Java documentation: "java.util.concurrent.Semaphore"
- Oracle Java documentation: "java.lang.Thread"
- Silberschatz, Galvin and Gagne, Operating System Concepts, Dining Philosophers Problem.
- Tanenbaum, Modern Operating Systems, process synchronization.
