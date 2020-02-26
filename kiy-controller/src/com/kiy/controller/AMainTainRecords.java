package com.kiy.controller;

import com.kiy.common.Device;
import com.kiy.resources.Lang;

/**
 * 查看设备维护记录
 * 
 * @author HLX
 *
 */
public class AMainTainRecords extends A<FMain> {

	public AMainTainRecords(FMain m) {
		super(m);
	}

	@Override
	protected void run() {
		Device d = main.getSelectionDevice();
		if (d != null) {

			FMaintainRecord f = new FMaintainRecord(main.getShell());
			main.getServerManager().add(f, d.getServo());
			f.open(d, main.getServerManager());
			main.getServerManager().off(f);

		} else {
			F.mbInformation(main.getShell(), Lang.getString("AMainTainRecords.MessageBoxMaintenanceRecordsTitle.text"), Lang.getString("AMainTainRecords.MessageBoxMaintenanceRecordsContent.text"));
		}
	}

}
