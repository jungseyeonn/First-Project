package login;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.JFrame;

public class LoginFrame extends WindowAdapter implements ActionListener {
	private JFrame f;
	private TextField tfid, tfpwd, tfmsg;
	private Choice userChoice;
	private Button bLogin, bJoin, bFpwd;
	private LoginDAO dao;
	private Font font;

	public LoginFrame() {
		dao = new LoginDAO();

		f = new JFrame("시흥시 학교 일정관리시스템 Login");
		f.setSize(500, 400);
		f.setLocation(700, 400);
		f.setLayout(null);
		f.addWindowListener(this);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().setBackground(Color.white);
		font = new Font("NEXON Lv1 Gothic", Font.BOLD, 13);
		
		userChoice = new Choice();
		userChoice.add("선생님");
		userChoice.add("개인");
		userChoice.setBounds(60, 30, 100, 40);
		userChoice.setFont(font);
		f.add(userChoice);

		Label usernameLabel = new Label("ID : ");
		usernameLabel.setFont(font);
		usernameLabel.setBounds(50, 80, 100, 40);
		f.add(usernameLabel);

		tfid = new TextField();
		tfid.setBounds(150, 80, 200, 40);
		tfid.setFont(new Font("NEXON Lv1 Gothic", Font.PLAIN,13));
		f.add(tfid);

		Label passwordLabel = new Label("Password : ");
		passwordLabel.setFont(font);
		passwordLabel.setBounds(50, 150, 100, 40);
		f.add(passwordLabel);

		tfpwd = new TextField();
		tfpwd.setBounds(150, 150, 200, 40);
		tfpwd.setEchoChar('*');
		f.add(tfpwd);

		tfmsg = new TextField();
		tfmsg.setBounds(60, 220, 380, 70);
		tfmsg.setEditable(false);
		tfmsg.setFocusable(false);
		tfmsg.setFont(font);
		f.add(tfmsg);

		bLogin = new Button("Login");
		bLogin.setBounds(380, 100, 80, 50);
		bLogin.addActionListener(this);
		bLogin.setBackground(Color.white);
		bLogin.setFont(font);
		f.add(bLogin);

		bJoin = new Button("회원가입");
		bJoin.setBounds(130, 310, 80, 30);
		bJoin.addActionListener(this);
		bJoin.setBackground(Color.white);
		bJoin.setFont(font);
		f.add(bJoin);

		bFpwd = new Button("비밀번호 찾기");
		bFpwd.setFont(font);
		bFpwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("비밀번호 찾기 페이지로 이동합니다.");
				FindPWD fp = new FindPWD();
			}
		});
		bFpwd.setBounds(270, 310, 100, 30);
		bFpwd.setBackground(Color.white);
		f.add(bFpwd);

		f.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == bJoin) {
			Join jo = new Join();
		}

		String strId = tfid.getText();
		String Udiv = userChoice.getSelectedItem();
		ArrayList<LoginVo> list = dao.list(strId, Udiv);

		if (list.size() == 1) {
			LoginVo data = (LoginVo) list.get(0);
			String sid = data.getId();
			String spwd = data.getPassword();

			if (tfpwd.getText().equals(spwd) && tfid.getText().equals(sid)) {
				if (userChoice.getSelectedItem().equals("선생님")) {
					System.out.println("선생님");
					TCalendar tc = new TCalendar();
					f.dispose();
				} else {
					System.out.println("개인");
					UCalendar uc = new UCalendar();
					f.dispose();
				}
			} else {
				tfmsg.setText("비밀번호가 올바르지 않습니다. 다시 확인해주세요.");
			}
		} else {
			tfmsg.setText("로그인 정보를 다시 확인해주세요.");
		}
	}

	public static void main(String[] args) {
		new LoginFrame();
	}
}
