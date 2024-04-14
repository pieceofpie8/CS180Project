/**
 * CS 180 Group Project - SMServerFinInterface
 *
 * Defines SMServerFinInterface, which is the interface of SMServerFin.
 */

public interface SMServerFinInterface {

    /**
     * Fields:
     * private static ExecutorService pool = Executors.newFixedThreadPool(10);
     * Creates a pool of threads so that multiple clients can connect at once. Initialized to
     * 10, but can be changed if necessary.
     * private static final int PORT = 12346; Defines the port number for the server
     * for use in the program.
     * private static MediaDatabase mediaDatabase; Defines the instance of MediaDatabase
     * that all threads will connect with to have updated and accurate information.
     * private static final Object lock = new Object(); Used to sync parts of the code
     * and make the threads safe to run.
     *
     * Constructors:
     * none
     *
     * Methods:
     * SMServerFin only contains a Main Method and a run method. This is done
     * through the use of a ClientHandler Class.
     */
}
