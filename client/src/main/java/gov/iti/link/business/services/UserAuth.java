package gov.iti.link.business.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.link.business.DTOs.UserDTO;

public class UserAuth {
    private static final String FILE_PATH = "user.info";
    public static void rememberUser() {
        UserDTO user = StateManager.getInstance().getUser();
        String userPhone = user.getPhone();
        String userPassword = user.getPassword();
        try {
            String contents = userPhone + '\n' + userPassword + '\n';
            Files.write(Paths.get("user.info"), contents.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isAuthorized() {
        Path path = Paths.get(FILE_PATH);
        List<String> lines = new ArrayList<String>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }

            if (lines.size() < 2)
                return false;
            String phone = lines.get(0);
            String password = lines.get(1);
            UserDTO user;

            user = ServiceManager.getInstance().getUserService().findByPhone(phone);
            if (user.getPassword().equals(password)) {
                StateManager.getInstance().setUser(user);
                return true;

            }

        } catch (IOException e) {
            return false;
        }

        return false;
    }

    public static void logOut(){
        try {
            boolean result = Files.deleteIfExists(Paths.get(FILE_PATH));
            if (result) {
                System.out.println("user.info deleted!");
            } else {
                System.out.println("unable to delete user.info");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
