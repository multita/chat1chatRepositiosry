package server.model;

import java.util.*;
import java.text.*;

public class testData {
   public static void main(String args[]) {

      Date now = new Date( );
      SimpleDateFormat ft = new SimpleDateFormat (" yyyy.MM.dd '����' hh:mm:ss ");

      System.out.println("Current Date: " + ft.format(now));
    //Current Date: �� 2016.11.01 at 01:37:56 ���� CST
} }
