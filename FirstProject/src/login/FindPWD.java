package login;

import java.awt.Button;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.JFrame;

public class FindPWD extends WindowAdapter implements ActionListener {
	private JFrame f;
	private TextField tfid, tfemail;
	private Button bok;

	public FindPWD() {
		f = new JFrame("비밀번호 찾기 - 이메일인증");
		f.setSize(400, 300);
		f.setLocation(600, 300);
		f.setLayout(null);
		f.addWindowListener(this);

		System.out.println("FIndPWD()");

		Label lid = new Label("아이디 : ");
		lid.setBounds(50, 60, 70, 40);
		f.add(lid);

		tfid = new TextField();
		tfid.setBounds(130, 60, 200, 30);
		f.add(tfid);

		Label lemail = new Label("이메일 : ");
		lemail.setBounds(50, 100, 70, 80);
		f.add(lemail);

		tfemail = new TextField();
		tfemail.setBounds(130, 120, 200, 30);
		f.add(tfemail);

		bok = new Button("인증하기");
		bok.setBounds(150, 190, 80, 40);
		bok.addActionListener(this);
		f.add(bok);

		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
