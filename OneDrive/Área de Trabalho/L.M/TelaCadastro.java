import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaCadastro extends JFrame {
    private JTextField campoNome;
    private JTextField campoSobrenome;
    private JTextField campoEmail;
    private JTextField campoNovoUsuario;
    private JPasswordField campoNovaSenha;

    public TelaCadastro() {
        setTitle("Tela de Cadastro");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        // Componentes
        campoNome = new JTextField();
        campoSobrenome = new JTextField();
        campoEmail = new JTextField();
        campoNovoUsuario = new JTextField();
        campoNovaSenha = new JPasswordField();

        add(new JLabel("Nome:"));
        add(campoNome);

        add(new JLabel("Sobrenome:"));
        add(campoSobrenome);

        add(new JLabel("E-mail:"));
        add(campoEmail);

        add(new JLabel("Novo Usuário:"));
        add(campoNovoUsuario);

        add(new JLabel("Nova Senha:"));
        add(campoNovaSenha);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarNovoUsuario();
            }
        });
        add(btnCadastrar);

        setVisible(true);
    }

    private void cadastrarNovoUsuario() {
        String nome = campoNome.getText();
        String sobrenome = campoSobrenome.getText();
        String email = campoEmail.getText();
        String novoUsuario = campoNovoUsuario.getText();
        char[] novaSenha = campoNovaSenha.getPassword();

        Autenticacao autenticacao = new Autenticacao();
        autenticacao.cadastrarUsuarioNoBanco(nome, sobrenome, email, novoUsuario, new String(novaSenha));

        JOptionPane.showMessageDialog(null, "Usuário cadastrado com sucesso!");
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaCadastro();
            }
        });
    }
}

class TelaLogin extends JFrame {
    private JTextField campoUsuario;
    private JPasswordField campoSenha;

    public TelaLogin() {
        // Configurações da janela
        setTitle("Tela de Login");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        // Componentes
        campoUsuario = new JTextField();
        campoSenha = new JPasswordField();

        add(new JLabel("Usuário:"));
        add(campoUsuario);

        add(new JLabel("Senha:"));
        add(campoSenha);

        JButton btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        add(btnLogin);

        JButton btnCadastro = new JButton("Cadastrar");
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTelaCadastro();
            }
        });
        add(btnCadastro);

        setVisible(true);
    }

    private void realizarLogin() {
        String usuario = campoUsuario.getText();
        char[] senha = campoSenha.getPassword();

        Autenticacao autenticacao = new Autenticacao();
        if (autenticacao.autenticarUsuario(usuario, senha)) {
            JOptionPane.showMessageDialog(null, "Login bem-sucedido!");
        } else {
            JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!");
        }
    }

    private void exibirTelaCadastro() {
        new TelaCadastro();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelaLogin();
            }
        });
    }
}
