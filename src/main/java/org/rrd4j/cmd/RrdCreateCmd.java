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

import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.Util;

import java.io.IOException;

class RrdCreateCmd extends RrdToolCmd {
	static final String DEFAULT_START = "now-10s";
	static final String DEFAULT_STEP = "300";

	private RrdDef rrdDef;

    @Override
	String getCmdType() {
		return "create";
	}

    @Override
	Object execute() throws IllegalArgumentException, IOException {
		String startStr = getOptionValue("b", "start", DEFAULT_START);
		long start = Util.getTimestamp(startStr);
		String stepStr = getOptionValue("s", "step", DEFAULT_STEP);
		long step = parseLong(stepStr);
		String[] words = getRemainingWords();
		if (words.length < 2) {
			throw new IllegalArgumentException("RRD file path not specified");
		}
		String path = words[1];
		rrdDef = new RrdDef(path, start, step);
		for (int i = 2; i < words.length; i++) {
			if (words[i].startsWith("DS:")) {
				parseDef(words[i]);
			}
			else if (words[i].startsWith("RRA:")) {
				parseRra(words[i]);
			}
			else {
				throw new IllegalArgumentException("Invalid rrdcreate syntax: " + words[i]);
			}
		}
		return createRrdDb();
	}

	private void parseDef(String word) throws IllegalArgumentException, IOException {
		// DEF:name:type:heratbeat:min:max
		String[] tokens = new ColonSplitter(word).split();
		if (tokens.length < 6) {
			throw new IllegalArgumentException("Invalid DS definition: " + word);
		}
		String dsName = tokens[1];
		DsType dsType = DsType.valueOf(tokens[2]);
		long heartbeat = parseLong(tokens[3]);
		double min = parseDouble(tokens[4]);
		double max = parseDouble(tokens[5]);
		rrdDef.addDatasource(dsName, dsType, heartbeat, min, max);
	}

	private void parseRra(String word) throws IllegalArgumentException, IOException {
		// RRA:cfun:xff:steps:rows
		String[] tokens = new ColonSplitter(word).split();
		if (tokens.length < 5) {
			throw new IllegalArgumentException("Invalid RRA definition: " + word);
		}
		ConsolFun cf = ConsolFun.valueOf(tokens[1]);
		double xff = parseDouble(tokens[2]);
		int steps = parseInt(tokens[3]);
		int rows = parseInt(tokens[4]);
		rrdDef.addArchive(cf, xff, steps, rows);
	}

	private String createRrdDb() throws IOException, IllegalArgumentException {
		RrdDb rrdDb = getRrdDbReference(rrdDef);
		releaseRrdDbReference(rrdDb);
		return rrdDef.getPath();
	}
}
