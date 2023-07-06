package scheduler;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FindPWD extends WindowAdapter implements ActionListener {
	private JFrame f, ff;
	private TextField tfid, tfemail, tfrepwd, tfrere;
	private Button bcon, brepwd;
	private LoginDAO dao;
	private Font font;

	public FindPWD() {
		dao = new LoginDAO();
		
		f = new JFrame("비밀번호 찾기 - 이메일인증");
		f.setSize(400, 300);
		f.setLocation(750, 450);
		f.setLayout(null);
		f.addWindowListener(this);
		f.getContentPane().setBackground(Color.white);

		System.out.println("FIndPWD()");

		Label lid = new Label("아이디 : ");
		lid.setBounds(50, 60, 70, 40);
		font = new Font("NEXON Lv1 Gothic", Font.BOLD, 13);
		lid.setFont(font);
		f.add(lid);

		tfid = new TextField();
		tfid.setBounds(130, 60, 200, 30);
		f.add(tfid);

		Label lemail = new Label("이메일 : ");
		lemail.setBounds(50, 100, 70, 80);
		lemail.setFont(font);
		f.add(lemail);

		tfemail = new TextField();
		tfemail.setBounds(130, 120, 200, 30);
		f.add(tfemail);

		bcon = new Button("인증하기");
		bcon.setBounds(150, 190, 80, 40);
		bcon.addActionListener(this);
		bcon.setBackground(Color.white);
		bcon.setFont(font);
		f.add(bcon);

		f.setVisible(true);

		ff = new JFrame("비밀번호 재설정");
		ff.setSize(500, 300);
		ff.setLocation(600, 300);
		ff.setLayout(null);
		ff.getContentPane().setBackground(Color.white);
		ff.addWindowListener(this);

		Label lrepwd = new Label("비밀번호 재설정 : ");
		lrepwd.setBounds(40, 60, 100, 40);
		ff.add(lrepwd);

		tfrepwd = new TextField();
		tfrepwd.setBounds(180, 60, 230, 30);
		tfrepwd.setEchoChar('*');
		ff.add(tfrepwd);

		Label lrere = new Label("비밀번호 재입력 : ");
		lrere.setBounds(40, 120, 100, 40);
		ff.add(lrere);

		tfrere = new TextField();
		tfrere.setBounds(180, 120, 230, 30);
		tfrere.setEchoChar('*');
		ff.add(tfrere);

		brepwd = new Button("비밀번호 재설정하기");
		brepwd.setBounds(170, 190, 130, 40);
		brepwd.setBackground(Color.white);
		brepwd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==brepwd) {
					if(tfrepwd.getText().equals(tfrere.getText())) {
						JOptionPane.showMessageDialog(null, "비밀번호가 변경되었습니다. 재로그인해주세요.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
						ff.dispose();
						repwd();
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호를 동일하게 입력해주세요.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		ff.add(brepwd);
	}

	private void repwd() {
		String id = tfid.getText();
		String pwd = tfrepwd.getText();
		System.out.println(pwd);

		dao.re(id, pwd);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String id = tfid.getText();
		String email = tfemail.getText();
		ArrayList<FoundPVo> listpwd = dao.listpwd(id, email);
	
		if (listpwd.size() == 1) {
			FoundPVo data = (FoundPVo) listpwd.get(0);
			String sid = data.getid();
			String semail = data.getemail();

			if (tfid.getText().equals(sid) && tfemail.getText().equals(semail)) {
				JOptionPane.showMessageDialog(null, "비밀번호를 재설정해주세요.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
				f.dispose();
				ff.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(null, "아이디와 이메일을 확인해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		} else if (tfid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else if (tfemail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "이메일을 입력해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "존재하지 않는 아이디 혹은 이메일입니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
		}

	}
}
