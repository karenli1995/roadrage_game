/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package roadcross;

import java.util.Arrays;

import javafx.collections.ObservableList;

public class LevelData {
	/*
	 * this class defines the information needed to load each level
	 */

    private static final String NEXT_LEVEL = "---";

    private static final String[] LEVELS_DATA = new String[] {
        "RRR", //in the first lane
        "UUU",
        "RRR",
        "UUU",
        "RRR",
        "",
        "RRR",
        "",
        "RRR",

        NEXT_LEVEL,

        "CCCC",
        "UUU",
        "CCCC",
        "UUU",
        "CCCC",
        "UUU",
        "CCCC",
        "UUU",
        "CCCC",

        NEXT_LEVEL,

        "UUUUU",
        "MMM",
        "UUUUU",
        "MMM",
        "CCCC",
        "MMM",
        "UUUUU",
        "MMM",
        "UUUUU",

        NEXT_LEVEL,

        "CCCC",
        "UUUUU",
        "CCCC",
        "UUUUU",
        "CCCC",
        "UUUUU",
        "CCCC",
        "UUUUU",
        "C",     
    };

    private static ObservableList<Integer> levelsOffsets;

    public static int getLevelsCount() {
        initLevelsOffsets();
        return levelsOffsets.size() - 1;
    }

    public static String[] getLevelData(int level) { //returns an array of what the level should look like
        initLevelsOffsets();
        if (level < 1 || level > getLevelsCount()) {
            return null;
        } else {
            return Arrays.copyOfRange(LEVELS_DATA, levelsOffsets.get(level - 1) + 1, levelsOffsets.get(level));
        }
    }

    private static void initLevelsOffsets() {
        if (levelsOffsets == null) {
            levelsOffsets = javafx.collections.FXCollections.<Integer>observableArrayList();
            levelsOffsets.add(-1);

            for (int i = 0; i < LEVELS_DATA.length; i++) {
                if (LEVELS_DATA[i].equals(NEXT_LEVEL)) {
                    levelsOffsets.add(i);
                }
            }
            levelsOffsets.add(LEVELS_DATA.length + 1); //changed from +1 //this is for each "NEXT_LEVEL" there is in LEVELS_DATA
            System.out.println("jen " + levelsOffsets);
        }
    }

}

