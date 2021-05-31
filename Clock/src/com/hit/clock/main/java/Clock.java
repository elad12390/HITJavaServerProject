package com.hit.clock.main.java;

public class Clock implements IClock {
    /**
     * השתמשתי בהרצה אסינכרונית של השעון וביצירת מחלקה בשם DateTime משלי, אשר תבצע את הוספת השניות פנימית בתוכה ותחזיק את הלוגיקה של הזמן הנוכחי
     */



    private static final int OneSecondMS = 1000;
    private DateTime dateTime;

    @Override
    public void start() {
        new Thread(this).start();
    }

    @Override
    public void run() {
        this.dateTime = new DateTime();

        while (true) {
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
