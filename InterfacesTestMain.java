import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * CS 180 Group Project - InterfacesTestMain
 *
 * Defines InterfacesTestMain, which contains a main method for the purpose of
 * testing if every class correctly implements their interface.
 */

public class InterfacesTestMain {
    public static void main(String[] args) {
        //test AccountInterface
        Account account = new Account("Name,Password,true:friendName:blockedName");
        if (account instanceof AccountInterface) {

            Method[] accountMethods = Account.class.getDeclaredMethods();
            Method[] accountInterfaceMethods = AccountInterface.class.getDeclaredMethods();

            //swapping interface methods
            for (int i = 0; i < accountInterfaceMethods.length; i++) {
                int holdCount = 0;

                //swapping account methods
                for (int j = 0; j < accountMethods.length; j++) {
                    if (accountInterfaceMethods[i].getName().equals(accountMethods[j].getName())
                            && Arrays.equals(accountInterfaceMethods[i].getParameterTypes(),
                            accountMethods[j].getParameterTypes())) {
                        holdCount++;
                    }
                }

                if (holdCount != 1) {
                    System.out.println("Account does not correctly implement AccountInterface");
                    break;
                }
            }

            System.out.println("Account correctly implements AccountInterface");

        } else {
            System.out.println("Account does not implement AccountInterface");
        }

        //test MediaDatabaseInterface
        MediaDatabase mediaDatabase = new MediaDatabase("accountsSaveFile", "directMessageFileNamesFile");
        if (mediaDatabase instanceof MediaDatabaseInterface) {

            Method[] mediaMethods = MediaDatabase.class.getDeclaredMethods();
            Method[] mediaInterfaceMethods = MediaDatabaseInterface.class.getDeclaredMethods();

            //swapping interface methods
            for (int i = 0; i < mediaInterfaceMethods.length; i++) {
                int holdCount = 0;

                //swapping media methods
                for (int j = 0; j < mediaMethods.length; j++) {
                    if (mediaInterfaceMethods[i].getName().equals(mediaMethods[j].getName())
                            && Arrays.equals(mediaInterfaceMethods[i].getParameterTypes(),
                            mediaMethods[j].getParameterTypes())) {
                        holdCount++;
                    }
                }

                if (holdCount != 1) {
                    System.out.println("MediaDatabase does not correctly implement MediaDatabaseInterface");
                    break;
                }
            }

            System.out.println("MediaDatabase correctly implements MediaDatabaseInterface");

        } else {
            System.out.println("MediaDatabase does not implement MediaDatabaseInterface");
        }

        //test SMServerFin
        SMServerFin server = new SMServerFin();
        if (server instanceof SMServerFin) {

            Method[] serverMethods = SMServerFin.class.getDeclaredMethods();
            Method[] serverInterfaceMethods = SMServerFinInterface.class.getDeclaredMethods();

            //swapping interface methods
            for (int i = 0; i < serverInterfaceMethods.length; i++) {
                int holdCount = 0;

                //swapping server methods
                for (int j = 0; j < serverMethods.length; j++) {
                    if (serverInterfaceMethods[i].getName().equals(serverMethods[j].getName())
                            && Arrays.equals(serverInterfaceMethods[i].getParameterTypes(),
                            serverMethods[j].getParameterTypes())) {
                        holdCount++;
                    }
                }

                if (holdCount != 1) {
                    System.out.println("SMServerFin does not correctly implement SMServerFinInterface");
                    break;
                }
            }

            System.out.println("SMServerFin correctly implements SMServerFinInterface");

        } else {
            System.out.println("SMServerFin does not implement SMServerFinInterface");
        }

        //test SMClientGUI
        try {
            SMClientGUI client = new SMClientGUI();
            if (client instanceof SMClientGUI) {

                Method[] clientMethods = SMClientGUI.class.getDeclaredMethods();
                Method[] clientInterfaceMethods = SMClientGUIInterface.class.getDeclaredMethods();

                //swapping interface methods
                for (int i = 0; i < clientInterfaceMethods.length; i++) {
                    int holdCount = 0;

                    //swapping client methods
                    for (int j = 0; j < clientMethods.length; j++) {
                        if (clientInterfaceMethods[i].getName().equals(clientMethods[j].getName())
                                && Arrays.equals(clientInterfaceMethods[i].getParameterTypes(),
                                clientMethods[j].getParameterTypes())) {
                            holdCount++;
                        }
                    }

                    if (holdCount != 1) {
                        System.out.println("SMClientGUI does not correctly implement SMClientGUIInterface");
                        break;
                    }
                }

                System.out.println("SMClientGUI correctly implements SMClientGUIInterface");

            } else {
                System.out.println("SMClientGUI does not implement SMClientGUIInterface");
            }
        }   catch (IOException e) {
            System.out.println("SMClientGUI failure");
        }
    }
}