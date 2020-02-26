package com.kiy.controller;

import org.eclipse.swt.widgets.MenuItem;

import com.kiy.client.CtrClient;
import com.kiy.common.Servo;
import com.kiy.common.User;
import com.kiy.protocol.Messages.Message.ActionCase;

public class MenuCommandUtil {
	private FMain fMain;
	private Servo servo;
	
	/**
	 * 用于主菜单需要选择伺服器时
	 * @param m
	 */
	public MenuCommandUtil(FMain m){
		fMain = m;
		servo = fMain.getCurrentServo();//拿到当前的伺服器
	}
	
	
	/**
	 * 用于其他窗口子菜单时
	 * @param s
	 */
	public MenuCommandUtil(Servo s){
		servo = s;
	}
	/**
	 * 通过权限设置菜单
	 * @param menuItems 菜单集合
	 */
	/**
	 * @param menuItems
	 */
	public void LoadMenuForCommand(MenuItem[] menuItems){
		/**
		 * step 1,判断伺服器是否连接
		 */
		if(servo!=null){
			CtrClient client = servo.getClient();
			
		if(client!=null&&client.isConnected()&&client.isRunning()){//
				/**
				 *  step 2,判断当前用户
				 */
				User currentUser = servo.getUser();//当前登录用户
				//将菜单权限与用户权限对应设置
				for(int i=0;i<menuItems.length;i++){
					Object data = menuItems[i].getData();
					Object data2 = menuItems[i].getData("tag");
					if(data2!=null){
						//需要判断选中状态的菜单,
					}else if(data!=null){
						if(data instanceof ActionCase[]){
							//先将伺服器连接后可以点击的菜单显示，将需要权限的菜单都先显示
							menuItems[i].setEnabled(true);
							ActionCase[] commands = (ActionCase[] )data;
							for (ActionCase command : commands) {  
								if(currentUser!=null){
									if(currentUser.allow(command.getNumber())){
										//拥有该权限
										menuItems[i].setEnabled(true);
										break;
									}else{
										//未拥有该权限
										menuItems[i].setEnabled(false);
									}
								}else{
									menuItems[i].setEnabled(false);
								}
								
							} 
							
						}else{
							//items的Data不是权限类型
						}
					}else{
						//有的菜单不需要考虑权限处理，不处理
					}
				}
				
				
				/**
				 *  step 3,判断当前伺服器
				 */
				if(fMain!=null){
//					Servo selectionServo = fMain.getSelectionServo();//当前选择的伺服器
				}
				
			}else{
				//伺服器未连接 将菜单置灰
				for(int j = 0;j<menuItems.length;j++){
					Object data = menuItems[j].getData();
					Object data2 = menuItems[j].getData("tag");
					if(data2!=null){
						//需要判断选中状态的菜单
					}else if(data!=null){
						ActionCase[] commands	=(ActionCase[])data;
						if(commands.length>0){
							menuItems[j].setEnabled(false);
						}else{
							//菜单权限为空
						}
						
					}else{
						//菜单data都为空，不处理
					}
				}
			}
		}else{
			//没有伺服器时
			for(int j = 0;j<menuItems.length;j++){
				Object data = menuItems[j].getData();
				Object data2 = menuItems[j].getData("tag");
				if(data2!=null){
					//需要判断选中状态的菜单
				}else if(data!=null){
					ActionCase[] commands	=(ActionCase[])data;
					if(commands.length>0){
						menuItems[j].setEnabled(false);
					}else{
						//菜单权限为空
					}
				}else{
					//菜单data都为空，不处理
				}	
			}
		}
	}
}
