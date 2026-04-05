package com.application.management.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimeFormatUtils {

	public static int parseEstimateToMinutes(String input) {

	    input = input.trim().toLowerCase();

	    int totalMinutes = 0;

	    // Match hours (supports decimals)
	    Pattern hourPattern = Pattern.compile("(\\d+(\\.\\d+)?)h");
	    Matcher hourMatcher = hourPattern.matcher(input);

	    if (hourMatcher.find()) {
	        double hours = Double.parseDouble(hourMatcher.group(1));
	        totalMinutes += (int) Math.round(hours * 60);
	    }

	    // Match minutes
	    Pattern minutePattern = Pattern.compile("(\\d+)m");
	    Matcher minuteMatcher = minutePattern.matcher(input);

	    if (minuteMatcher.find()) {
	        totalMinutes += Integer.parseInt(minuteMatcher.group(1));
	    }

	    // If pure number (no h/m), treat as minutes
	    if (!input.contains("h") && !input.contains("m")) {
	        totalMinutes += Integer.parseInt(input);
	    }

	    if (totalMinutes < 0) {
	        throw new IllegalArgumentException("Estimate cannot be negative");
	    }

	    return totalMinutes;
	}
}
