import java.lang.reflect.Method;
import java.util.Arrays;

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
    }
}