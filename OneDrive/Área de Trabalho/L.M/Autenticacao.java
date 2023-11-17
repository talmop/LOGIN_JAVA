import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Autenticacao {

    private static final String URL = "jdbc:mysql://localhost:3306/";
    private static final String NOME_BD = "usuario";
    private static final String USUARIO_BD = "root";
    private static final String SENHA_BD = "12345";

    public Autenticacao() {
        criarBancoETabela();
    }

    private void criarBancoETabela() {
        try (Connection conexao = DriverManager.getConnection(URL, USUARIO_BD, SENHA_BD)) {
            // Criação do banco de dados
            try (Statement stmt = conexao.createStatement()) {
                String sqlCriarBanco = "CREATE DATABASE IF NOT EXISTS " + NOME_BD;
                stmt.executeUpdate(sqlCriarBanco);
            }

            // Seleciona o banco de dados
            try (Statement stmt = conexao.createStatement()) {
                String sqlUsarBanco = "USE " + NOME_BD;
                stmt.executeUpdate(sqlUsarBanco);
            }

            // Criação da tabela
            try (Statement stmt = conexao.createStatement()) {
                String sqlCriarTabela = "CREATE TABLE IF NOT EXISTS usuario ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY,"
                        + "nome VARCHAR(50),"
                        + "sobrenome VARCHAR(50),"
                        + "email VARCHAR(50),"
                        + "nomeUsuario VARCHAR(50),"
                        + "senha VARCHAR(50))";
                stmt.executeUpdate(sqlCriarTabela);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cadastrarUsuarioNoBanco(String nome, String sobrenome, String email, String nomeUsuario, String senha) {
        try (Connection conexao = DriverManager.getConnection(URL + NOME_BD, USUARIO_BD, SENHA_BD)) {
            String query = "INSERT INTO usuario (nome, sobrenome, email, nomeUsuario, senha) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conexao.prepareStatement(query)) {
                pstmt.setString(1, nome);
                pstmt.setString(2, sobrenome);
                pstmt.setString(3, email);
                pstmt.setString(4, nomeUsuario);
                pstmt.setString(5, senha);

                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public boolean autenticarUsuario(String usuario, char[] senha) {
        try (Connection conexao = DriverManager.getConnection(URL + NOME_BD, USUARIO_BD, SENHA_BD)) {
            String query = "SELECT * FROM usuario WHERE nomeUsuario = ? AND senha = ?";
            try (PreparedStatement pstmt = conexao.prepareStatement(query)) {
                pstmt.setString(1, usuario);
                pstmt.setString(2, new String(senha));

                try (ResultSet resultSet = pstmt.executeQuery()) {
                    return resultSet.next(); // Se houver um resultado, o usuário é autenticado
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao autenticar usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        // Exemplo de uso
        Autenticacao autenticacao = new Autenticacao();
        autenticacao.cadastrarUsuarioNoBanco("Exemplo", "Usuario", "exemplo@email.com", "exemploUsuario", "senha123");

        if (autenticacao.autenticarUsuario("exemploUsuario", "senha123".toCharArray())) {
            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
        }
    }
}
