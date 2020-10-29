package ui;

import entity.IPBean;
import utils.MyTableModel;
import utils.Mysql;
import utils.browse;
import utils.getIpList;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.Font;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.table.TableColumn;

public class main extends JFrame {

	private JPanel contentPane;
	private Vector columnNames = new Vector();
	private Vector data = new Vector();
	private MyTableModel tmModel = null;
	private JTable table;
	private JTextField textField;
	private JLabel label_3;
	int temp = 1;
	getIpList getipList = new getIpList();
	Mysql mysql = new Mysql();
	browse brow = new browse();
	List<IPBean> allList =new ArrayList<IPBean>();


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				main frame = new main();
				frame.setVisible(true);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 539);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setTitle("zengxiaochao");
		contentPane.setLayout(null);



		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 551, 432);
		contentPane.add(scrollPane);


		columnNames.addElement("ID");
		columnNames.addElement("Ip");
		columnNames.addElement("Port");
		columnNames.addElement("Type");
		columnNames.addElement("Location");
		int sum = fres();

		table = new JTable(tmModel);

		scrollPane.setViewportView(table);
		textField = new JTextField();
		textField.setFont(new Font("宋体", Font.PLAIN, 17));
		textField.setText("https://blog.csdn.net/qq_41170600/article/details/108953925");
		textField.setBounds(67, 452, 494, 38);
		contentPane.add(textField);
		textField.setColumns(10);

		JLabel label = new JLabel("网址：");
		label.setFont(new Font("宋体", Font.PLAIN, 17));
		label.setBounds(20, 452, 98, 38);
		contentPane.add(label);

		JButton btnNewButton = new JButton("开始");
		btnNewButton.setFont(new Font("宋体", Font.PLAIN, 24));
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					String URL = textField.getText();
					for(int i=0;i<allList.size();i++)
					{
						IPBean ipb = allList.get(i);
						new Thread(new Runnable() {
							@Override
							public void run() {
								brow.f(ipb,URL);
							}
						}).start();
					}
				} catch (Exception exception) {
				}
			}
		});

		btnNewButton.setBounds(571, 445, 203, 47);
		contentPane.add(btnNewButton);
		JButton button = new JButton("更新代理数据库");
		button.setFont(new Font("宋体", Font.PLAIN, 24));
		button.setBounds(571, 170, 203, 47);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					getipList.getAllList(temp);
					temp+=20;
				} catch (Exception exception) {
				}
			}
		});
		JButton button2 = new JButton("刷新");
		button2.setFont(new Font("宋体", Font.PLAIN, 24));
		button2.setBounds(571, 111, 203, 47);
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					label_3.setText(String.valueOf(fres()));
				} catch (Exception exception) {
				}
			}
		});
		contentPane.add(button);
		contentPane.add(button2);

		JLabel label_1 = new JLabel("共");
		label_1.setFont(new Font("宋体", Font.PLAIN, 45));
		label_1.setBounds(591, 49, 64, 52);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("个");
		label_2.setFont(new Font("宋体", Font.PLAIN, 45));
		label_2.setBounds(710, 49, 64, 52);
		contentPane.add(label_2);

		label_3 = new JLabel(String.valueOf(sum));
		label_3.setFont(new Font("宋体", Font.PLAIN, 45));
		label_3.setBounds(649, 49, 64, 52);
		contentPane.add(label_3);
	}
	public int fres() {
		clearDb();
		allList = mysql.getList();
		data.clear();
		for(int i=0;i<allList.size();i++)
		{
			Vector row = new Vector();
			row.addElement(i);
			row.addElement(allList.get(i).getIp());
			row.addElement(allList.get(i).getPort());
			row.addElement(allList.get(i).getType());
			row.addElement(allList.get(i).getLoc());
			data.addElement(row);
		}
		tmModel = new MyTableModel();
		tmModel.setDataVector(data, columnNames);
		System.out.println("刷新完成");
		return allList.size();
	}
	public void clearDb() {
		return ;
	}
}
