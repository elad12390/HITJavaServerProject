package com.hit.clock.main.java;

import java.util.concurrent.atomic.AtomicBoolean;

public class Clock implements IClock {
    /**
     * השתמשתי בהרצה אסינכרונית של השעון וביצירת מחלקה בשם DateTime משלי, אשר תבצע את הוספת השניות פנימית בתוכה ותחזיק את הלוגיקה של הזמן הנוכחי
     */

    private static final int OneSecondMS = 1000;
    public AtomicBoolean isRunning = new AtomicBoolean(false);
    private DateTime dateTime;

    @Override
    public void start() {
        isRunning.set(true);
        new Thread(this).start();
    }

    public void stop() {
        isRunning.set(false);
    }

    @Override
    public void run() {
        this.dateTime = new DateTime();
        while (isRunning.get()) {
            try {
                Thread.sleep(OneSecondMS);
                dateTime.addSecond();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getTime() {
        return dateTime.toString();
    }

}
