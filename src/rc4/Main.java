package rc4;

import java.io.UnsupportedEncodingException;
import java.math.*;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {

    String chave, entrada;
    byte[] encrypted;
    String decrypted;

    public Main() {
        initComponents();
        JOptionPane.showMessageDialog(this,
                    "Digite o texto a ser encriptado em RC4 e a chave, o programa retornará os bytes encriptados",
                    "Ajuda",
                    JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(this,
                    "Para decriptar, basta copiar os bytes para o campo \"Mensagem\" e pressionar \"decriptar\"",
                    "Ajuda",
                    JOptionPane.WARNING_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonEncriptar = new javax.swing.JButton();
        jTextFieldEntrada = new javax.swing.JTextField();
        jTextFieldChave = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButtonDecriptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        labelResultado = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Algoritmo RC4");

        jButtonEncriptar.setText("Encriptar");
        jButtonEncriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEncriptarActionPerformed(evt);
            }
        });

        jLabel1.setText("Digite a mensagem:");

        jLabel2.setText("Digite a senha:");

        jButtonDecriptar.setText("Decriptar");
        jButtonDecriptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDecriptarActionPerformed(evt);
            }
        });

        labelResultado.setColumns(20);
        labelResultado.setLineWrap(true);
        labelResultado.setRows(5);
        jScrollPane1.setViewportView(labelResultado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldEntrada, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(jTextFieldChave))))
                        .addGap(53, 53, 53))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonEncriptar)
                        .addGap(76, 76, 76)
                        .addComponent(jButtonDecriptar)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldChave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonEncriptar)
                    .addComponent(jButtonDecriptar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonEncriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEncriptarActionPerformed
        chave = jTextFieldChave.getText();
        entrada = jTextFieldEntrada.getText();

        encriptar();

        String resultado = "";

        for (int i = 0; i < encrypted.length; i++) {
            resultado = resultado.concat(String.format("%8s", Integer.toBinaryString(encrypted[i] & 0xFF)).replace(' ', '0'));
        }
        labelResultado.setText(resultado);
    }//GEN-LAST:event_jButtonEncriptarActionPerformed

    private void jButtonDecriptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDecriptarActionPerformed
        chave = jTextFieldChave.getText();
        entrada = jTextFieldEntrada.getText();

        decriptar();

        labelResultado.setText(decrypted);
    }//GEN-LAST:event_jButtonDecriptarActionPerformed

    private void decriptar() {
        Cipher cipher = null;
        KeyGenerator kg = null;
        SecureRandom sr = new SecureRandom(chave.getBytes());

        try {
            cipher = Cipher.getInstance("RC4");
            kg = KeyGenerator.getInstance("RC4");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        kg.init(sr);
        SecretKey sk = kg.generateKey();

        try {
            cipher.init(Cipher.DECRYPT_MODE, sk);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        byte[] array = getByteByString(entrada);

        try {
            decrypted = new String(cipher.doFinal(array));
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Para decriptar, favor inserir bytes binários válidos",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private byte[] getByteByString(String binaryString) {
        int length = binaryString.length();
        if (length % 8 != 0) {
            return null;
        }
        byte[] result = new byte[length / 8];

        int start = 0;
        int finish = 7;
        for (int i = 0; i < (length / 8); i++) {
            String temp = binaryString.substring(start, finish + 1);
            Integer byteAsInt = Integer.parseInt(temp, 2);
            result[i] = byteAsInt.byteValue();

            start += 8;
            finish += 8;
        }

        return result;
    }

    private void encriptar() {
        Cipher cipher = null;
        KeyGenerator kg = null;
        SecureRandom sr = new SecureRandom(chave.getBytes());

        try {
            cipher = Cipher.getInstance("RC4");
            kg = KeyGenerator.getInstance("RC4");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        kg.init(sr);
        SecretKey sk = kg.generateKey();

        try {
            cipher.init(Cipher.ENCRYPT_MODE, sk);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            encrypted = cipher.doFinal(entrada.getBytes());
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonDecriptar;
    private javax.swing.JButton jButtonEncriptar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldChave;
    private javax.swing.JTextField jTextFieldEntrada;
    private javax.swing.JTextArea labelResultado;
    // End of variables declaration//GEN-END:variables
}
