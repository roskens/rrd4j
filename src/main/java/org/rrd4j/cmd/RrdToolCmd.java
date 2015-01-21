/*******************************************************************************
 * Copyright (c) 2001-2005 Sasa Markovic and Ciaran Treanor.
 * Copyright (c) 2011 The OpenNMS Group, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *******************************************************************************/

package org.rrd4j.cmd;

import java.util.List;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDbPool;
import org.rrd4j.core.RrdDef;

import java.io.IOException;

abstract class RrdToolCmd {

	private RrdCmdScanner cmdScanner;

	abstract String getCmdType();

	abstract Object execute() throws IOException;

	Object executeCommand(String command) throws IOException {
		cmdScanner = new RrdCmdScanner(command);
		return execute();
	}

	Object executeCommand(List<String> command) throws IOException {
		cmdScanner = new RrdCmdScanner(command);
		return execute();
	}

	String getOptionValue(String shortForm, String longForm) throws IOException {
		return cmdScanner.getOptionValue(shortForm, longForm);
	}

	String getOptionValue(String shortForm, String longForm, String defaultValue) throws IOException {
		return cmdScanner.getOptionValue(shortForm, longForm, defaultValue);
	}

	String[] getMultipleOptionValues(String shortForm, String longForm) throws IOException {
		return cmdScanner.getMultipleOptions(shortForm, longForm);
	}

	boolean getBooleanOption(String shortForm, String longForm) {
		return cmdScanner.getBooleanOption(shortForm, longForm);
	}

	String[] getRemainingWords() {
		return cmdScanner.getRemainingWords();
	}

	static boolean rrdDbPoolUsed = true;
	static boolean standardOutUsed = true;

	static boolean isRrdDbPoolUsed() {
		return rrdDbPoolUsed;
	}

	static void setRrdDbPoolUsed(boolean rrdDbPoolUsed) {
		RrdToolCmd.rrdDbPoolUsed = rrdDbPoolUsed;
	}

	static boolean isStandardOutUsed() {
		return standardOutUsed;
	}

	static void setStandardOutUsed(boolean standardOutUsed) {
		RrdToolCmd.standardOutUsed = standardOutUsed;
	}

	static long parseLong(String value) throws IOException {
		try {
			return Long.parseLong(value);
		}
		catch (NumberFormatException nfe) {
			throw new IOException(nfe);
		}
	}

	static int parseInt(String value) throws IOException {
		try {
			return Integer.parseInt(value);
		}
		catch (NumberFormatException nfe) {
			throw new IOException(nfe);
		}
	}

	static double parseDouble(String value) throws IOException {
		if (value.equals("U")) {
			return Double.NaN;
		}
		try {
			return Double.parseDouble(value);
		}
		catch (NumberFormatException nfe) {
			throw new IOException(nfe);
		}
	}

	static void print(String s) {
		if (standardOutUsed) {
			System.out.print(s);
		}
	}

	static void println(String s) {
		if (standardOutUsed) {
			System.out.println(s);
		}
	}

	static RrdDb getRrdDbReference(String path) throws IOException {
		if (rrdDbPoolUsed) {
			return RrdDbPool.getInstance().requestRrdDb(path);
		}
		else {
			return new RrdDb(path);
		}
	}

	static RrdDb getRrdDbReference(String path, String xmlPath) throws IOException {
		if (rrdDbPoolUsed) {
			return RrdDbPool.getInstance().requestRrdDb(path, xmlPath);
		}
		else {
			return new RrdDb(path, xmlPath);
		}
	}

	static RrdDb getRrdDbReference(RrdDef rrdDef) throws IOException {
		if (rrdDbPoolUsed) {
			return RrdDbPool.getInstance().requestRrdDb(rrdDef);
		}
		else {
			return new RrdDb(rrdDef);
		}
	}

	static void releaseRrdDbReference(RrdDb rrdDb) throws IOException {
		if (rrdDbPoolUsed) {
			RrdDbPool.getInstance().release(rrdDb);
		}
		else {
			rrdDb.close();
		}
	}
}
