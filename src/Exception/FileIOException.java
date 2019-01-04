package Exception;

import javax.swing.*;

public class FileIOException extends Exception {

    private String desc;

    public FileIOException(String desc) {
        this.desc = desc;
    }

    public void showMessageDialog() {
        JOptionPane.showMessageDialog(null,desc,"File IO Error",JOptionPane.ERROR_MESSAGE);
    }

}
