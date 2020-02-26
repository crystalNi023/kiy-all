/**
 * 2017年3月24日
 */
package com.kiy.servo.test;

import java.util.Calendar;

import com.kiy.common.Task;
import com.kiy.common.Types.Day;
import com.kiy.common.Types.Month;
import com.kiy.common.Types.Week;
import com.kiy.servo.Config;
import com.kiy.servo.Executor;
import com.kiy.servo.job.Scheduler;
import com.kiy.servo.job.Strategy;

/**
 * @author Simon(ZhangXi TEL:13883833982)
 *
 */
public final class TestScheduler {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Config.load();
		Executor.initialize();

		Strategy.initialize();

		Calendar c = Calendar.getInstance();
		c.clear(Calendar.MILLISECOND);

		c.add(Calendar.SECOND, 10);
		Task task = new Task();
		task.setName("一次性任务");
		task.setStart(c.getTime());
		task.setMonth(0);
		task.setDay(0);
		task.setWeek(0);
		task.setInterval(0);
		task.setRepeat(0);
		task.setStop(c.getTime());

		Scheduler s = new Scheduler(task);
		s.start();

		task = new Task();
		task.setName("按天重复任务");
		task.setStart(c.getTime());
		task.setMonth(0);
		task.setDay(0);
		task.setWeek(0);
		task.setInterval(0);
		task.setRepeat(0);
		c.add(Calendar.MONTH, 1);
		task.setStop(c.getTime());

		s = new Scheduler(task);
		s.start();

		task = new Task();
		task.setName("按周重复任务");
		task.setStart(c.getTime());
		task.setMonth(0);
		task.setDay(0);
		task.setWeek(0);
		task.margeWeek(Week.MONDAY);
		task.margeWeek(Week.TUESDAY);
		task.setInterval(0);
		task.setRepeat(0);
		c.add(Calendar.MONTH, 1);
		task.setStop(c.getTime());

		s = new Scheduler(task);
		s.start();

		task = new Task();
		task.setName("按月重复任务");
		task.setStart(c.getTime());
		task.setMonth(0);
		task.margeMonth(Month.MARCH);
		task.margeMonth(Month.APRIL);
		task.setDay(0);
		task.margeDay(Day.LAST);
		task.setWeek(0);
		task.setInterval(0);
		task.setRepeat(0);
		c.add(Calendar.MONTH, 1);
		task.setStop(c.getTime());

		s = new Scheduler(task);
		s.start();

		Thread.currentThread().join();
	}
}