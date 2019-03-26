package br.edu.ifce.pigeon.jobs;

import java.util.concurrent.Semaphore;

public class UserThread extends Thread {
    private User user;
    private Mail mail;
    public int maail_number;
    public int max_capacity;
    private boolean alive;

    private static Semaphore semaphore_pigeon = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);
    private static Semaphore mail_box = new Semaphore(M); //esse semaforo tem que ser instanciado num scopo global em relação as threads

    public UserThread(int writeTime) {
        this.user = new User(writeTime);
        this.alive = true;
    }

    public void fire() {
        this.alive = false;
    }

    @Override
    public void run() {
        while (this.alive) {
            try {
                Write_mail();
                mutex.acquire();
                mail_box.acquire();
                // num_mail++
                Save_mail()
                // if (num_mail % N == 0){
                //    mutex.release();
                ///}
                mutex.release()
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void Write_mail() {
        this.mail = new Mail(this.user);
    }

    private void Save_mail() {
        MailBox.getInstance().put(this.mail);
    }


}

