package com.avaya.jtapi.tsapi.util;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

public class PerfStatisticsCollector {
	static class PerfHistoryBean {
		private long historyMin;
		private long historyMax;
		private long windowMin;
		private long windowMax;
		private long windowSum;
		private long windowCount;
		private long historySum;
		private int historyCount;
		private final String name;
		private boolean historyInitialized = false;

		public PerfHistoryBean(final String name) {
			historySum = 0L;
			historyCount = 0;
			this.name = name;
		}

		public void copyToHistory(final Vector<Long> windowDetails,
				final int count) {
			if (windowDetails.size() == 0)
				return;
			long sum = 0L;
			windowMax = windowMin = windowDetails.firstElement().longValue();

			for (final Long value : windowDetails) {
				sum += value.longValue();
				if (value.longValue() < windowMin)
					windowMin = value.longValue();
				if (value.longValue() > windowMax)
					windowMax = value.longValue();
			}
			windowSum = sum;
			windowCount = count;
			if (sum != 0L) {
				historySum += sum;
				historyCount += count;
				if (!historyInitialized) {
					historyMin = windowMin;
					historyInitialized = true;
				} else if (windowMin < historyMin)
					historyMin = windowMin;
				if (windowMax > historyMax)
					historyMax = windowMax;
			}
		}

		public void printHistoryStats(final Logger logger) {
			if (historyCount != 0)
				logger.info(name + ": Min = " + historyMin + "\tMax = "
						+ historyMax + "\tAverage = " + (float) historySum
						/ historyCount);
			else
				logger.info(name + ": Min = " + historyMin + "\tMax = "
						+ historyMax + "\tAverage = " + 0);
		}

		public void printWindowStats(final Logger logger) {
			if (windowSum != 0L)
				logger.info(name + ": Min = " + windowMin + "\tMax = "
						+ windowMax + "\tAverage = " + (float) windowSum
						/ (float) windowCount);
			else
				logger.info(name + ": Min = " + windowMin + "\tMax = "
						+ windowMax + "\tAverage = " + 0);
		}
	}

	static class PerfStatisticsCalculator {
		private static Logger logger = Logger
				.getLogger("jtapi.performanceLogger");

		public static void printMaxAverage(final String name,
				final Vector<Long> data) {
			long max = 0L;
			float average = 0.0F;
			if (data.size() > 0) {
				for (final Long dataValue : data) {
					if (dataValue.longValue() > max)
						max = dataValue.longValue();
					average += dataValue.longValue();
				}
				average /= data.size();
			}
			PerfStatisticsCalculator.logger.info(name + ": Max = " + max
					+ "\tAverage = " + average);
		}

		public static void printMinMaxAverageData(final Vector<Float> data,
				final String name) {
			if (data.size() == 0)
				return;
			float min = data.get(0).floatValue();
			float max = 0.0F;
			float average = 0.0F;
			if (data.size() > 0) {
				for (final Float dataValue : data) {
					if (dataValue.floatValue() < min)
						min = dataValue.floatValue();
					else if (dataValue.floatValue() > max)
						max = dataValue.floatValue();
					average += dataValue.floatValue();
				}
				average /= data.size();
			}
			PerfStatisticsCalculator.logger.info(name + " Min = " + min
					+ "\tMax = " + max + "\tAverage = " + average);
		}

		private Vector<Long> data;
		private long min;
		private long max;
		private float average;
		private long historyMin;
		private long historMax;

		private float historyAverage;

		private long window;

		public PerfStatisticsCalculator() {
		}

		public PerfStatisticsCalculator(final Vector<Long> theData,
				final long window) {
			data = theData;
			this.window = window;
		}

		private void computeHistoryMinMaxAvg() {
			if (data.size() == 0)
				return;
			historyMin = data.get(0).longValue();
			historMax = 0L;
			historyAverage = 0.0F;
			if (data.size() > 0) {
				for (final Long tempData : data) {
					final long dataValue = tempData.longValue();
					if (dataValue < historyMin)
						historyMin = dataValue;
					if (dataValue > historMax)
						historMax = dataValue;
					historyAverage += dataValue;
				}
				historyAverage /= data.size();
			}
		}

		public void computeMinMaxAverage() {
			computeHistoryMinMaxAvg();
			computeMinMaxAvgWindow();
		}

		private void computeMinMaxAvgWindow() {
			if (data.size() == 0)
				return;
			min = data.get(0).longValue();
			max = 0L;
			average = 0.0F;
			if (window > 0L) {
				for (int i = (int) (data.size() - window); i < data.size(); ++i) {
					final long dataValue = data.get(i).longValue();
					if (dataValue < min)
						min = dataValue;
					if (dataValue > max)
						max = dataValue;
					average += dataValue;
				}
				average /= window;
			}
		}

		public float getAverage() {
			return average;
		}

		public Vector<Long> getData() {
			return data;
		}

		public long getHistorMax() {
			return historMax;
		}

		public float getHistoryAverage() {
			return historyAverage;
		}

		public long getHistoryMin() {
			return historyMin;
		}

		public long getMax() {
			return max;
		}

		public long getMin() {
			return min;
		}

		public long getWindow() {
			return window;
		}

		public void reset() {
			min = max = historMax = historyMin = window = 0L;
			average = historyAverage = 0.0F;
			data.clear();
			data = null;
		}

		public void setData(final Vector<Long> data) {
			this.data = data;
		}

		public void setWindow(final long window) {
			this.window = window;
		}
	}

	private static class PerfTimerTask extends TimerTask {
		private void compute(final Vector<Long> statData, final int counter,
				final PerfStatisticsCollector.PerfStatisticsCalculator perfBean) {
			perfBean.setData(statData);
			perfBean.setWindow(counter);
			perfBean.computeMinMaxAverage();
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public void run() {
			// PerfStatisticsCollector.access$108();
			Vector<Long> queueLengthCloneVector;
			int queueLengthCloneCounter;
			int callCountClone;
			int eventCountClone;
			synchronized (PerfStatisticsCollector.class) {
				final Vector<Long> unsolCloneVector = (Vector<Long>) PerfStatisticsCollector.unsolicitedHandlingTime
						.clone();
				final int unsolCloneCounter = PerfStatisticsCollector.unsolicitedHandlingTimeCounter
						.getAndSet(0);
				PerfStatisticsCollector.unsolicitedHandlingTime.clear();
				PerfStatisticsCollector.unsolHandlingTimeHistory.copyToHistory(
						unsolCloneVector, unsolCloneCounter);

				final Vector<Long> serviceTurnaroundCloneVector = (Vector) PerfStatisticsCollector.serviceRequestTurnaroundTime
						.clone();
				final int serviceTurnaroundCloneCounter = PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter
						.getAndSet(0);
				PerfStatisticsCollector.serviceRequestTurnaroundTime.clear();
				PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory
						.copyToHistory(serviceTurnaroundCloneVector,
								serviceTurnaroundCloneCounter);

				queueLengthCloneVector = (Vector) PerfStatisticsCollector.queueLength
						.clone();
				PerfStatisticsCollector.queueLength.clear();
				queueLengthCloneCounter = PerfStatisticsCollector.queueLengthCounter
						.getAndSet(0);

				final Vector<Long> messageLatencyCloneVector = (Vector) PerfStatisticsCollector.messageLatency
						.clone();
				final int messageLatencyCloneCounter = PerfStatisticsCollector.messageLatencyCounter
						.getAndSet(0);
				PerfStatisticsCollector.messageLatency.clear();
				PerfStatisticsCollector.messageLatencyHistory.copyToHistory(
						messageLatencyCloneVector, messageLatencyCloneCounter);

				callCountClone = PerfStatisticsCollector.callCount.getAndSet(0);

				eventCountClone = PerfStatisticsCollector.eventCount
						.getAndSet(0);
			}
			final PerfStatisticsCollector.PerfStatisticsCalculator qPerfCalc = new PerfStatisticsCollector.PerfStatisticsCalculator();
			compute(queueLengthCloneVector, queueLengthCloneCounter, qPerfCalc);

			PerfStatisticsCollector.logger
					.info("Statistics for current window : ");
			PerfStatisticsCollector.unsolHandlingTimeHistory
					.printWindowStats(PerfStatisticsCollector.logger);
			PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory
					.printWindowStats(PerfStatisticsCollector.logger);
			PerfStatisticsCollector.messageLatencyHistory
					.printWindowStats(PerfStatisticsCollector.logger);

			if (queueLengthCloneVector.size() > 0)
				PerfStatisticsCollector.logger.info("QUEUE LENGTH: Current = "
						+ queueLengthCloneVector.lastElement() + "\tMax = "
						+ qPerfCalc.getMax());
			if (callCountClone != 0) {
				final float currentCallsPerSecond = callCountClone
						/ PerfStatisticsCollector.performanceWindow;
				PerfStatisticsCollector.logger.info("CALLS PER SECOND: "
						+ currentCallsPerSecond);
				// PerfStatisticsCollector.access$1716(currentCallsPerSecond);
				if (!PerfStatisticsCollector.callHistoryInitialized) {
					// PerfStatisticsCollector.access$1802(true);
					// PerfStatisticsCollector.access$1902(PerfStatisticsCollector.access$2002(currentCallsPerSecond));
				}
				if (currentCallsPerSecond < PerfStatisticsCollector.minCallHistory)
					// PerfStatisticsCollector.access$1902(currentCallsPerSecond);
					if (currentCallsPerSecond > PerfStatisticsCollector.maxCallHistory) {
						// PerfStatisticsCollector.access$2002(currentCallsPerSecond);
					}
			}
			if (eventCountClone != 0) {
				final float currentEventsPerSecond = eventCountClone
						/ PerfStatisticsCollector.performanceWindow;
				PerfStatisticsCollector.logger.info("EVENTS PER SECOND:"
						+ currentEventsPerSecond);
				// PerfStatisticsCollector.access$2116(currentEventsPerSecond);
				if (!PerfStatisticsCollector.eventHistoryInitialized) {
					// PerfStatisticsCollector.access$2202(true);
					// PerfStatisticsCollector.access$2302(PerfStatisticsCollector.access$2402(currentEventsPerSecond));
				}
				if (currentEventsPerSecond < PerfStatisticsCollector.minEventHistory)
					// PerfStatisticsCollector.access$2302(currentEventsPerSecond);
					if (currentEventsPerSecond > PerfStatisticsCollector.maxEventHistory) {
						// PerfStatisticsCollector.access$2402(currentEventsPerSecond);
					}
			}

			PerfStatisticsCollector.logger
					.info("Cumulative Statistics Since Performance Monitoring Started :");
			PerfStatisticsCollector.unsolHandlingTimeHistory
					.printHistoryStats(PerfStatisticsCollector.logger);
			PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory
					.printHistoryStats(PerfStatisticsCollector.logger);
			PerfStatisticsCollector.messageLatencyHistory
					.printHistoryStats(PerfStatisticsCollector.logger);
			final long currentQMax = qPerfCalc.getMax();
			// PerfStatisticsCollector.access$2514(qPerfCalc.getMax());
			if (currentQMax > PerfStatisticsCollector.queueOverallMax) {
				// PerfStatisticsCollector.access$2602(currentQMax);
			}
			PerfStatisticsCollector.logger.info("QUEUE LENGTH: Max = "
					+ PerfStatisticsCollector.queueOverallMax + "\tAverage = "
					+ (float) PerfStatisticsCollector.queueMaxWindowSum
					/ (float) PerfStatisticsCollector.numWindowsRun);
			if (callCountClone != 0)
				PerfStatisticsCollector.logger.info("CALLS PER SECOND: Min = "
						+ PerfStatisticsCollector.minCallHistory + "\tMax = "
						+ PerfStatisticsCollector.maxCallHistory
						+ "\tAverage = "
						+ PerfStatisticsCollector.callCountHistorySum
						/ PerfStatisticsCollector.numWindowsRun);
			if (eventCountClone != 0)
				PerfStatisticsCollector.logger.info("EVENTS PER SECOND: Min = "
						+ PerfStatisticsCollector.minEventHistory + "\tMax = "
						+ PerfStatisticsCollector.maxEventHistory
						+ "\tAverage = "
						+ PerfStatisticsCollector.eventCountHistorySum
						/ PerfStatisticsCollector.numWindowsRun);
		}
	}

	// private static final String PROP_PERF_LOGGER_NAME =
	// "jtapi.performanceLogger";

	private static Timer perfTimer;

	private static boolean perfWindowChanged = true;
	private static Logger logger = Logger.getLogger("jtapi.performanceLogger");
	private static int performanceWindow = 60;
	private static long unsolicitedHandlingTimeThreshold = 100L;
	private static long serviceRequestTurnaroundTimeThreshold = 100L;
	private static long queueLengthThreshold = 10L;
	private static long messageLatencyThreshold = 20L;
	private static long numWindowsRun = 0L;
	private static AtomicInteger callCount;
	private static float callCountHistorySum = 0.0F;
	private static float minCallHistory;
	private static float maxCallHistory;
	private static boolean callHistoryInitialized = false;
	private static AtomicInteger eventCount;
	private static float eventCountHistorySum = 0.0F;
	private static float minEventHistory;
	private static float maxEventHistory;
	private static boolean eventHistoryInitialized = false;
	private static Vector<Long> unsolicitedHandlingTime;
	private static PerfHistoryBean unsolHandlingTimeHistory;
	private static AtomicInteger unsolicitedHandlingTimeCounter;
	private static Vector<Long> serviceRequestTurnaroundTime;
	private static PerfHistoryBean serviceRequestTurnaroundTimeHistory;
	private static AtomicInteger serviceRequestTurnaroundTimeCounter;
	private static Vector<Long> queueLength;
	private static long queueOverallMax;
	private static long queueMaxWindowSum;
	private static AtomicInteger queueLengthCounter;
	private static Vector<Long> messageLatency;

	private static PerfHistoryBean messageLatencyHistory;

	private static AtomicInteger messageLatencyCounter;

	private static boolean isInitialized = false;

	public static int getPerformanceWindow() {
		return PerfStatisticsCollector.performanceWindow;
	}

	private static void initializeDataStructures() {
		PerfStatisticsCollector.callCount = new AtomicInteger();
		PerfStatisticsCollector.callCountHistorySum = 0.0F;

		PerfStatisticsCollector.eventCount = new AtomicInteger();
		PerfStatisticsCollector.eventCountHistorySum = 0.0F;

		PerfStatisticsCollector.unsolHandlingTimeHistory = new PerfHistoryBean(
				"UNSOLICITED HANDLING TIME");
		PerfStatisticsCollector.unsolicitedHandlingTime = new Vector<Long>();
		PerfStatisticsCollector.unsolicitedHandlingTimeCounter = new AtomicInteger();

		PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory = new PerfHistoryBean(
				"SERVICE REQUEST TURNAROUND TIME");
		PerfStatisticsCollector.serviceRequestTurnaroundTime = new Vector<Long>();
		PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter = new AtomicInteger();

		PerfStatisticsCollector.queueLength = new Vector<Long>();
		PerfStatisticsCollector.queueMaxWindowSum = 0L;
		PerfStatisticsCollector.queueOverallMax = 0L;
		PerfStatisticsCollector.queueLengthCounter = new AtomicInteger();

		PerfStatisticsCollector.messageLatencyHistory = new PerfHistoryBean(
				"MESSAGE LATENCY");
		PerfStatisticsCollector.messageLatency = new Vector<Long>();
		PerfStatisticsCollector.messageLatencyCounter = new AtomicInteger();
	}

	public static void initPerfStatisticsCollector() {
		if (!PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.initializeDataStructures();
			if (PerfStatisticsCollector.perfTimer != null) {
				PerfStatisticsCollector.perfTimer.cancel();
				PerfStatisticsCollector.perfTimer = null;
			}
			PerfStatisticsCollector.perfTimer = new Timer(true);
			PerfStatisticsCollector.perfTimer.schedule(new PerfTimerTask(),
					PerfStatisticsCollector.performanceWindow * 1000,
					PerfStatisticsCollector.performanceWindow * 1000);
			PerfStatisticsCollector.logger
					.info("##########Performance statistics monitoring start##########");
			PerfStatisticsCollector.isInitialized = true;
			PerfStatisticsCollector.perfWindowChanged = false;
		}
	}

	private static void resetDataStructures() {
		PerfStatisticsCollector.unsolicitedHandlingTime.clear();
		PerfStatisticsCollector.unsolicitedHandlingTime = null;
		PerfStatisticsCollector.unsolicitedHandlingTimeCounter = null;
		PerfStatisticsCollector.unsolHandlingTimeHistory = null;
		PerfStatisticsCollector.numWindowsRun = 0L;

		PerfStatisticsCollector.serviceRequestTurnaroundTime.clear();
		PerfStatisticsCollector.serviceRequestTurnaroundTime = null;
		PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter = null;
		PerfStatisticsCollector.serviceRequestTurnaroundTimeHistory = null;

		PerfStatisticsCollector.queueLength.clear();
		PerfStatisticsCollector.queueOverallMax = 0L;
		PerfStatisticsCollector.queueLength = null;
		PerfStatisticsCollector.queueLengthCounter = null;

		PerfStatisticsCollector.messageLatency.clear();
		PerfStatisticsCollector.messageLatency = null;
		PerfStatisticsCollector.messageLatencyCounter = null;
		PerfStatisticsCollector.messageLatencyHistory = null;

		PerfStatisticsCollector.callCount = null;
		PerfStatisticsCollector.minCallHistory = PerfStatisticsCollector.maxCallHistory = PerfStatisticsCollector.callCountHistorySum = 0.0F;
		PerfStatisticsCollector.callHistoryInitialized = false;

		PerfStatisticsCollector.eventCount = null;
		PerfStatisticsCollector.eventCountHistorySum = PerfStatisticsCollector.minEventHistory = PerfStatisticsCollector.maxEventHistory = 0.0F;
		PerfStatisticsCollector.eventHistoryInitialized = false;

		PerfStatisticsCollector.perfTimer.cancel();
		PerfStatisticsCollector.perfTimer = null;

		PerfStatisticsCollector.isInitialized = false;
	}

	public static void setMessageLatencyThreshold(
			final long messageLatencyThreshold) {
		PerfStatisticsCollector.messageLatencyThreshold = messageLatencyThreshold;
	}

	public static void setPerformanceWindow(final int performanceWindow) {
		if (PerfStatisticsCollector.performanceWindow != performanceWindow)
			PerfStatisticsCollector.perfWindowChanged = true;
		PerfStatisticsCollector.performanceWindow = performanceWindow;
	}

	public static void setQueueLengthThreshold(final long queueLengthThreshold) {
		PerfStatisticsCollector.queueLengthThreshold = queueLengthThreshold;
	}

	public static void setServiceRequestTurnaroundTimeThreshold(
			final long serviceRequestTurnaroundTimeThreshold) {
		PerfStatisticsCollector.serviceRequestTurnaroundTimeThreshold = serviceRequestTurnaroundTimeThreshold;
	}

	public static void setUnsolicitedHandlingTimeThreshold(
			final long unsolicitedHandlingTimeThreshold) {
		PerfStatisticsCollector.unsolicitedHandlingTimeThreshold = unsolicitedHandlingTimeThreshold;
	}

	public static void shutdown() {
		if (PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.resetDataStructures();
			PerfStatisticsCollector.logger
					.info("##########Performance statistics monitoring stop##########");
		}
	}

	public static void updateCallCount() {
		if (PerfStatisticsCollector.isInitialized)
			PerfStatisticsCollector.callCount.incrementAndGet();
	}

	public static void updateEventCount() {
		if (PerfStatisticsCollector.isInitialized)
			PerfStatisticsCollector.eventCount.incrementAndGet();
	}

	public static void updateMessageLatency(final long newValue) {
		if (PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.messageLatency.add(Long.valueOf(newValue));
			PerfStatisticsCollector.messageLatencyCounter.incrementAndGet();
			if (newValue > PerfStatisticsCollector.messageLatencyThreshold)
				PerfStatisticsCollector.logger
						.info("Message latency threshold value crossed. New value is : "
								+ newValue);
		}
	}

	public static void updatePerfStatisticsCollectorConfig() {
		if (PerfStatisticsCollector.perfWindowChanged) {
			PerfStatisticsCollector.resetDataStructures();
			PerfStatisticsCollector.initializeDataStructures();
			PerfStatisticsCollector.perfTimer = new Timer(true);
			PerfStatisticsCollector.perfTimer.schedule(new PerfTimerTask(),
					PerfStatisticsCollector.performanceWindow * 1000,
					PerfStatisticsCollector.performanceWindow * 1000);
			PerfStatisticsCollector.perfWindowChanged = false;
		}
	}

	public static void updateQueueLength(final long newValue) {
		if (PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.queueLength.add(new Long(newValue));
			PerfStatisticsCollector.queueLengthCounter.incrementAndGet();
			if (newValue > PerfStatisticsCollector.queueLengthThreshold)
				PerfStatisticsCollector.logger
						.info("Queue length threshold value crossed. New value is : "
								+ newValue);
		}
	}

	public static void updateServiceRequestTurnaroundTime(final long newValue) {
		if (PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.serviceRequestTurnaroundTime.add(new Long(
					newValue));
			PerfStatisticsCollector.serviceRequestTurnaroundTimeCounter
					.incrementAndGet();
			if (newValue > PerfStatisticsCollector.serviceRequestTurnaroundTimeThreshold)
				PerfStatisticsCollector.logger
						.info("Service request turnaround time threshold value crossed. New value is : "
								+ newValue);
		}
	}

	public static void updateUnsolicitedHandlingTime(final long newValue) {
		if (PerfStatisticsCollector.isInitialized) {
			PerfStatisticsCollector.unsolicitedHandlingTime.add(new Long(
					newValue));
			PerfStatisticsCollector.unsolicitedHandlingTimeCounter
					.incrementAndGet();
			if (newValue > PerfStatisticsCollector.unsolicitedHandlingTimeThreshold)
				PerfStatisticsCollector.logger
						.info("Unsolicited handling time threshold value crossed. New value is : "
								+ newValue);
		}
	}
}
