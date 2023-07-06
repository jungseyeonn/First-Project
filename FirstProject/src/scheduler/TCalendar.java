package scheduler;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

class TCalendar extends JFrame implements ActionListener {
	MainDAO dao = new MainDAO();

	public Choice cd, csh, csc;

	String[] days = { "일", "월", "화", "수", "목", "금", "토" };
	int year, month, day, todays, memoday = 0;

	Font f;
	Calendar today, cal;

	JButton btnBefore, btnAfter, btnAdd, btnDel, badd, bdel;
	JButton[] calBtn = new JButton[49];

	JLabel jlSchool, jlScedule;

	JPanel panWest, panSouth, panEast, panNorth;

	JTextField txtMonth, txtYear, txtTime;
	JTextArea txtschool, txtschedule;
	BorderLayout bLayout = new BorderLayout();

	public TCalendar() {
		today = Calendar.getInstance();
		cal = new GregorianCalendar();
		year = today.get(Calendar.YEAR);
		month = today.get(Calendar.MONTH) + 1;// 1월의 값이 0

		panNorth = new JPanel();
		panNorth.setBackground(Color.white);
		panNorth.add(btnBefore = new JButton("Before"));
		panNorth.add(txtYear = new JTextField(year + "년"));
		panNorth.add(txtMonth = new JTextField(month + "월"));
		txtYear.setEnabled(false);
		txtMonth.setEnabled(false);

		panNorth.add(btnAfter = new JButton("After"));
		f = new Font("NEXON Lv1 Gothic", Font.BOLD, 26);
		txtYear.setFont(f);
		txtMonth.setFont(f);
		panNorth.add(btnAdd = new JButton("일정추가"));
		panNorth.add(btnDel = new JButton("일정삭제"));

		add(panNorth, "North");

		// 오늘 날짜 출력
		JLabel lb1 = new JLabel();
		lb1.setFont(new Font("NEXON Lv1 Gothic", Font.BOLD, 15));
		String t1 = "오늘날짜 : " + today.get(Calendar.YEAR) + " 년 " + (today.get(Calendar.MONTH) + 1) + " 월 "
				+ today.get(Calendar.DAY_OF_MONTH) + " 일";
		lb1.setText(t1);
		lb1.setForeground(Color.orange);
		panNorth.add(lb1);

		// 달력
		panWest = new JPanel(new GridLayout(7, 7));// 격자나,눈금형태의 배치관리자

		gridInit();
		calSet();
		hideInit();
		add(panWest, "West");

		panEast = new JPanel();
		panEast.setBackground(Color.white);
		panEast.setLayout(new FlowLayout());
		add(panEast);

		f = new Font("NEXON Lv1 Gothic", Font.BOLD, 13);

		panEast.add(jlSchool = new JLabel("학교명"));
		jlSchool.setFont(f);

		txtschool = new JTextArea();
		txtschool.setPreferredSize(new Dimension(130, 800));
		txtschool.setEditable(false);
		txtschool.setFocusable(false);
		txtschool.setFont(new Font("NEXON Lv1 Gothic", Font.BOLD, 13));
		panEast.add(txtschool);

		JScrollPane nameScroll = new JScrollPane(txtschool); // JTextArea를 JScrollPane로 감싸기
		nameScroll.setPreferredSize(new Dimension(180, 300)); // 스크롤 패널의 우선 크기 설정
		nameScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panEast.add(nameScroll); // JScrollPane를 패널에 추가

		panEast.add(jlScedule = new JLabel("오늘일정"));
		jlScedule.setFont(f);

		txtschedule = new JTextArea();
		txtschedule.setPreferredSize(new Dimension(130, 800));
		txtschedule.setEditable(false);
		txtschedule.setFocusable(false);
		txtschedule.setFont(new Font("NEXON Lv1 Gothic", Font.BOLD, 13));
		panEast.add(txtschedule);

		JScrollPane scheduleScroll = new JScrollPane(txtschedule);
		scheduleScroll.setPreferredSize(new Dimension(180, 300));
		scheduleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panEast.add(scheduleScroll);

		btnBefore.addActionListener(this);
		btnBefore.setFont(f);
		btnAfter.addActionListener(this);
		btnAfter.setFont(f);
		btnAdd.addActionListener(this);
		btnAdd.setFont(f);
		btnDel.addActionListener(this);
		btnDel.setFont(f);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("시흥시 일정관리 시스템 - 선생님");
		setBounds(590, 380, 850, 400);
		setVisible(true);
	}

	public void calSet() {
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, (month - 1));
		cal.set(Calendar.DATE, 1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		/*
		 * get 및 set 를 위한 필드치로, 요일을 나타냅니다. 이 필드의 값은
		 * SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY, 및 SATURDAY get()메소드의 의해 요일이
		 * 숫자로 반환
		 */

		int j = 0;
		int hopping = 0;
		calBtn[0].setForeground(new Color(255, 0, 0));// 일요일 "일"
		calBtn[6].setForeground(new Color(0, 0, 255));// 토요일 "토"
		for (int i = cal.getFirstDayOfWeek(); i < dayOfWeek; i++) {
			j++;
		}
		// 일요일부터 그달의 첫시작 요일까지 빈칸으로 셋팅하기 위해
		hopping = j;
		for (int kk = 0; kk < hopping; kk++) {
			calBtn[kk + 7].setText("");
		}
		for (int i = cal.getMinimum(Calendar.DAY_OF_MONTH); i <= cal.getMaximum(Calendar.DAY_OF_MONTH); i++) {
			cal.set(Calendar.DATE, i);
			if (cal.get(Calendar.MONTH) != month - 1) {
				break;
			}
			todays = i;
			if (memoday == 1) {
				calBtn[i + 6 + hopping].setForeground(new Color(0, 255, 0));
			} else {
				calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 0));
				if ((i + hopping - 1) % 7 == 0) {// 일요일
					calBtn[i + 6 + hopping].setForeground(new Color(255, 0, 0));
				}
				if ((i + hopping) % 7 == 0) {// 토요일
					calBtn[i + 6 + hopping].setForeground(new Color(0, 0, 255));
				}
			}
			// 요일을 찍은 다음부터 계산해야 하니 요일을 찍은 버튼의 갯수를 더하고 인덱스가 0부터 시작이니 -1을 해준 값으로 연산을 해주고 버튼의
			// 색깔을 변경해준다.
			calBtn[i + 6 + hopping].setText((i) + "");
		}
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == btnBefore) {
			this.panWest.removeAll();
			calInput(-1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (ae.getSource() == btnAfter) {
			this.panWest.removeAll();
			calInput(1);
			gridInit();
			panelInit();
			calSet();
			hideInit();
			this.txtYear.setText(year + "년");
			this.txtMonth.setText(month + "월");
		} else if (ae.getSource() == btnAdd) {
			calSet();
			txtschool.setText("");
			txtschedule.setText("");

			// 일정 추가하는 창
			JFrame af = new JFrame("일정추가");
			af.setLayout(null);
			af.setVisible(true);
			af.setSize(500, 300);
			af.getContentPane().setBackground(Color.white);
			af.setFont(f);
			af.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					af.dispose();
				}
			});
			af.setLocationRelativeTo(null);

			JLabel l = new JLabel("※ 날짜를 선택하지 않을 시, 오늘 날짜가 입력됩니다.");
			l.setBounds(100, 30, 350, 20);
			l.setFont(new Font("NEXON Lv1 Gothic", Font.PLAIN, 12));
			l.setForeground(Color.red);
			af.add(l);

			UtilDateModel model = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(model);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
			datePicker.setBackground(Color.white);
			af.add(datePicker);
			datePicker.setBounds(100, 50, 280, 40);
			datePicker.setVisible(true);

			cd = new Choice();
			cd.add("초등학교");
			cd.add("중학교");
			cd.add("고등학교");
			cd.setBounds(35, 100, 100, 40);
			cd.setFont(f);
			af.add(cd);

			MainDAO md = new MainDAO();
			ArrayList<MainVo> chosc = md.chosc(getName());
			csh = new Choice();
			csh.add("구분을 선택해주세요.");
			cd.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						String idx = cd.getSelectedItem().toString();
						if (idx.equals("초등학교")) {
							csh.removeAll();
							for (int i = 0; i < 50; i++) {
								csh.add(chosc.get(i).getName());
							}
						} else if (idx.equals("중학교")) {
							csh.removeAll();
							for (int i = 51; i < 76; i++) {
								csh.add(chosc.get(i).getName());
							}
						} else if (idx.equals("고등학교")) {
							csh.removeAll();
							for (int i = 76; i < 94; i++) {
								csh.add(chosc.get(i).getName());
							}
						}
					}
				}
			});

			csh.setBounds(165, 100, 160, 40);
			csh.setFont(f);
			af.add(csh);

			csc = new Choice();
			csc.add("졸업식");
			csc.add("공사");
			csc.add("체험학습");
			csc.setBounds(355, 100, 90, 40);
			csc.setFont(f);
			af.add(csc);

			badd = new JButton("일정추가");
			badd.setBounds(180, 180, 100, 30);
			badd.setBackground(Color.white);
			badd.setFont(f);
			af.add(badd);
			badd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (csh.getSelectedItem().toString().equals("구분을 선택해주세요.")) {
						JOptionPane.showMessageDialog(null, "구분을 선택해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else {
						String div = cd.getSelectedItem();
						String name = csh.getSelectedItem();
						String schedule = csc.getSelectedItem();
						int year = model.getYear();
						int month = model.getMonth() + 1;
						int day = model.getDay();
						dao.insert(div, name, schedule, year, month, day);
						JOptionPane.showMessageDialog(null, "일정이 추가되었습니다.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					}
				}
			});

			// 일정삭제
		} else if (ae.getSource() == btnDel) {
			calSet();
			txtschool.setText("");
			txtschedule.setText("");

			JFrame df = new JFrame("일정삭제");
			df.setLayout(null);
			df.setVisible(true);
			df.setSize(500, 300);
			df.getContentPane().setBackground(Color.white);
			df.setFont(f);
			df.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					df.dispose();
				}
			});

			df.setLocationRelativeTo(null);

			JLabel l = new JLabel("※ 날짜를 선택하지 않을 시, 오늘 날짜가 입력됩니다.");
			l.setBounds(100, 30, 350, 20);
			l.setFont(new Font("NEXON Lv1 Gothic", Font.PLAIN, 12));
			l.setForeground(Color.red);
			df.add(l);

			UtilDateModel model = new UtilDateModel();
			JDatePanelImpl datePanel = new JDatePanelImpl(model);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel);
			df.add(datePicker);
			datePicker.setBounds(100, 50, 280, 40);
			datePicker.setBackground(Color.white);
			datePicker.setVisible(true);

			cd = new Choice();
			cd.add("초등학교");
			cd.add("중학교");
			cd.add("고등학교");
			cd.setBounds(35, 100, 100, 40);
			cd.setFont(f);
			df.add(cd);

			MainDAO md = new MainDAO();
			ArrayList<MainVo> chosc = md.chosc(getName());
			csh = new Choice();
			csh.add("구분을 선택해주세요.");
			cd.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						String idx = cd.getSelectedItem().toString();
						if (idx.equals("초등학교")) {
							csh.removeAll();
							for (int i = 0; i < 50; i++) {
								csh.add(chosc.get(i).getName());
							}
						} else if (idx.equals("중학교")) {
							csh.removeAll();
							for (int i = 50; i < 76; i++) {
								csh.add(chosc.get(i).getName());
							}
						} else if (idx.equals("고등학교")) {
							csh.removeAll();
							for (int i = 76; i < 94; i++) {
								csh.add(chosc.get(i).getName());
							}
						}
					}
				}
			});

			csh.setBounds(165, 100, 160, 40);
			csh.setFont(f);
			df.add(csh);

			csc = new Choice();
			csc.add("졸업식");
			csc.add("공사");
			csc.add("체험학습");
			csc.setBounds(355, 100, 90, 40);
			csc.setFont(f);
			df.add(csc);

			bdel = new JButton("일정삭제");
			bdel.setBounds(180, 180, 100, 30);
			bdel.setBackground(Color.white);
			bdel.setFont(f);
			df.add(bdel);

			bdel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String div = cd.getSelectedItem();
					String name = csh.getSelectedItem();
					String schedule = csc.getSelectedItem();
					int year = model.getYear();
					int month = model.getMonth() + 1;
					int day = model.getDay();
					if (csh.getSelectedItem().toString().equals("구분을 선택해주세요.")) {
						JOptionPane.showMessageDialog(null, "구분을 선택해주세요.", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else if (dao.delete(div, name, schedule, year, month, day) == 0) {
						JOptionPane.showMessageDialog(null, "해당 일정은 없습니다.", "ERROR", JOptionPane.ERROR_MESSAGE);
					} else {
						dao.delete(div, name, schedule, year, month, day);
						JOptionPane.showMessageDialog(null, "일정이 삭제되었습니다.", "PLAIN_MESSAGE", JOptionPane.PLAIN_MESSAGE);
					}
				}
			});
		} else if (Integer.parseInt(ae.getActionCommand()) >= 1 && Integer.parseInt(ae.getActionCommand()) <= 31) {
			day = Integer.parseInt(ae.getActionCommand());
			// 버튼의 밸류 즉 1,2,3.... 문자를 정수형으로 변환하여 클릭한 날짜를 바꿔준다.
			// 선택한 버튼 색깔 변경
//			calBtn[day+10].setBackground(new Color(153, 204, 255));
			calSet();

			// 달력에 일자 누르면 DB에 저장된 일정뜸
			ArrayList<MainVo> listca = dao.listca(year, month, day);

			if (listca.size() != 0) {
				for (int i = 0; i < listca.size(); i++) {
					MainVo data = (MainVo) listca.get(i);
					String sd = data.getSch();
					String nd = data.getName();
					if (i == 0) {
						txtschool.setText((i + 1) + " " + nd + "\n");
						txtschedule.setText((i + 1) + " " + sd + "\n");
					} else {
						txtschool.append((i + 1) + " " + nd + "\n");
						txtschedule.append((i + 1) + " " + sd + "\n");
					}
				}
			} else {
				txtschool.setText("");
				txtschedule.setText("일정이 없습니다.");
			}
		}
	}

	public void hideInit() {
		for (int i = 0; i < calBtn.length; i++) {
			if ((calBtn[i].getText()).equals(""))
				calBtn[i].setEnabled(false);
			// 일이 찍히지 않은 나머지 버튼을 비활성화 시킨다.
			calBtn[i].setBackground(Color.white);
		}
	}

	public void gridInit() {
		// jPanel3에 버튼 붙이기
		for (int i = 0; i < days.length; i++)
			panWest.add(calBtn[i] = new JButton(days[i]));
		for (int i = days.length; i < 49; i++) {
			panWest.add(calBtn[i] = new JButton(""));
			calBtn[i].addActionListener(this);
		}
	}

	public void panelInit() {
		GridLayout gridLayout1 = new GridLayout(7, 7);
		panWest.setLayout(gridLayout1);
	}

	public void calInput(int gap) {
		month += (gap);
		if (month <= 0) {
			month = 12;
			year = year - 1;
		} else if (month >= 13) {
			month = 1;
			year = year + 1;
		}
	}
}