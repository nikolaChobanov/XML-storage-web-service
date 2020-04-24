package docu.journal.service;

import docu.journal.exception.TooManyUsersException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Log4j2
@Service
//Semaphore to hold out on 4 requests at a time
public class ClientLimitManager {

    private static final int MAX_NUMBER_OF_THREADS = 4;
    static Semaphore semaphore = new Semaphore(MAX_NUMBER_OF_THREADS);

    public static boolean acquireAvailableSpace() {

        Boolean failAcquire = false;

        if (!(semaphore.tryAcquire())) {
            System.out.println("fail");
            log.error("Server could not perform request server is currently overused");
            throw new TooManyUsersException("Server could not perform request server is currently overused");
        }

      /* to test user limit
       try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return failAcquire;
    }

    public static void releaseAcquiredSpace() {

        log.info("Server successfully returned get request ");
        semaphore.release();
    }


}
