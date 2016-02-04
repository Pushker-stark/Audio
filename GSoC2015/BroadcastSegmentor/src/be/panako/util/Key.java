/***************************************************************************
*                                                                          *                     
* Panako - acoustic fingerprinting                                         *   
* Copyright (C) 2014 - Joren Six / IPEM                                    *   
*                                                                          *
* This program is free software: you can redistribute it and/or modify     *
* it under the terms of the GNU Affero General Public License as           *
* published by the Free Software Foundation, either version 3 of the       *
* License, or (at your option) any later version.                          *
*                                                                          *
* This program is distributed in the hope that it will be useful,          *
* but WITHOUT ANY WARRANTY; without even the implied warranty of           *
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the            *
* GNU Affero General Public License for more details.                      *
*                                                                          *
* You should have received a copy of the GNU Affero General Public License *
* along with this program.  If not, see <http://www.gnu.org/licenses/>     *
*                                                                          *
****************************************************************************
*    ______   ________   ___   __    ________   ___   ___   ______         *
*   /_____/\ /_______/\ /__/\ /__/\ /_______/\ /___/\/__/\ /_____/\        *      
*   \:::_ \ \\::: _  \ \\::\_\\  \ \\::: _  \ \\::.\ \\ \ \\:::_ \ \       *   
*    \:(_) \ \\::(_)  \ \\:. `-\  \ \\::(_)  \ \\:: \/_) \ \\:\ \ \ \      * 
*     \: ___\/ \:: __  \ \\:. _    \ \\:: __  \ \\:. __  ( ( \:\ \ \ \     * 
*      \ \ \    \:.\ \  \ \\. \`-\  \ \\:.\ \  \ \\: \ )  \ \ \:\_\ \ \    * 
*       \_\/     \__\/\__\/ \__\/ \__\/ \__\/\__\/ \__\/\__\/  \_____\/    *
*                                                                          *
****************************************************************************
*                                                                          *
*                              Panako                                      * 
*                       Acoustic Fingerprinting                            *
*                                                                          *
****************************************************************************/


package be.panako.util;

/**
 * Defines which values can be configured and their respective
 * default values.
 * @author Joren Six
 */
public enum Key{
	/**
	 * Checks the data store if the file # is already added. Set this to
	 * false if a large number of unique files are added for a small
	 * performance gain.
	 */
	CHECK_DUPLICATE_FILE_NAMES("TRUE"), 
	
	/**
	 * The maximum file size (in bytes) of files that are stored. Default is 60 megabytes.
	 */
	MAX_FILE_SIZE("62914560"),
	
	/**
	 * The step size while monitoring a long audio fragment, in seconds.
	 */
	MONITOR_STEP_SIZE("25"),

	/**
	 * The overlap, also in seconds. By default detection resolution is 
	 * 25-5=20 seconds.
	 */
	MONITOR_OVERLAP("5"),
	
	
	/**
	 * Enabling JLibAV allows support for almost all audio formats in the
	 * known universe. When disabled Panako supports Flac, MP3, and Vorbis,
	 * using buggy Java implementations Configuring libAV and enabling
	 * JLibAV is strongly advised (see readme.txt).
	 */
	DECODER("PIPE"),
	
	/**
	 * The pipe command environment
	 */
	DECODER_PIPE_ENVIRONMENT("/bin/bash"),
	/**
	 * The pipe command argument
	 */
	DECODER_PIPE_ENVIRONMENT_ARG("-c"),

	/**
	 * The command that streams PCM audio to a pipe
	 */
	DECODER_PIPE_COMMAND("avconv -i \"%resource%\" -vn -ar %sample_rate% -ac %channels% -sample_fmt s16 -f wav pipe:1"),

	/**
	 * The buffer used to cache the results from 
	 * the pipe. 44100 bytes is half a second.
	 */
	DECODER_PIPE_BUFFER_SIZE("44100"),
	
	/**
	 * The log file for the pipe decoder.
	 */
	DECODER_PIPE_LOG_FILE("decoder_log.txt"),
	
	
	/**
	 * The number of processors available to Panako. If zero (or less) all
	 * available processors are used.
	 */
	AVAILABLE_PROCESSORS("0"), 
	
	
	
	
	
	
	/**
	 * The strategy (algorithm) to use, CTEQ|FFT.
	 */
	STRATEGY("CTEQ"),
	

	/**
	 * The name of the MapDB database location.
	 */
	CTEQ_MAPDB_DATABASE("cteq_panako_db"),
	/**
	 * The expected sample rate for the constant q transform.
	 */
	CTEQ_SAMPLE_RATE(44100),
	/**
	 * Step size in samples for the constant q transform.
	 */
	CTEQ_STEP_SIZE(1536),
	/**
	 * The minimum pitch, in absolute cents 3383cents is about +-77 Hz.
	 */
	CTEQ_MIN_FREQ(3700),
	/**
	 * The maximum pitch, in absolute cents. 11533 cents is about +-6392.63 Hz.
	 */
	CTEQ_MAX_FREQ(12200),
	/**
	 * The number of bins per octave for the constant q transform
	 */
	CTEQ_BINS_PER_OCTAVE(36),
	/**
	 * The maximum number of event points generated for each second of analyzed
	 * audio to store in the database (Hz).
	 */
	CTEQ_EVENT_POINTS_PER_SECOND_FOR_STORAGE(8),
	/**
	 * The number of event points per audio second for queries (Hz)
	 */
	CTEQ_EVENT_POINTS_PER_SECOND_FOR_QUERY(8),
	/**
	 * The maximum delta between two frequency components in one 
	 * fingerprint, in cents
	 */
	CTEQ_EVENT_POINT_FREQUENCY_DELTA_MAX(1066),
	/**
	 * Defines how much fingerprints can be connected to one 
	 * event point in the spectrum. Increasing this factor improves 
	 * retrieval rate but limits performance and storage needs.
	 */
	CTEQ_EVENT_POINT_BRANCHING_FOR_STORAGE(1),
	/**
	 * The branching factor for a query needs to be higher to make sure
	 * matches are found, but not too high, so no unneeded hash collisions
	 * are found.
	 */
	CTEQ_EVENT_POINT_BRANCHING_FOR_QUERY(4),
	/**
	 * The maximum number of hash collisions allowed in storage. It is a
	 * trade-off between recall and response time. More hash collisions
	 * means a larger search time, but more hits. Allowing more collisions
	 * also increases disk space.
	 */
	 CTEQ_MAX_HASH_COLLISIONS(1000),
	 
	/**
	 * The minimum number of fingerprints that 
	 * need to match between a query and the reference audio. 
	 * The aim is to remove random fingerprint matches.
	 * If this is set too high, some real matches may be discarded. 
	 * Set it too low, and the matching algorithm will spend a lot of time 
	 * checking random matches.  
	 */
	CTEQ_MINIMUM_MATCHES_THRESHOLD(4),
	/**
	 * The matching algorithm detects a real match if at least this number of fingerprints align. 
	 * If it is set too low, false positives may appear. If it is set too high some real matches
	 * may be ignored. 
	 */
	CTEQ_MINIMUM_ALIGNED_MATCHES_THRESHOLD(3), 
	
	
	FFT_SAMPLE_RATE(11025),
	
	FFT_SIZE(512),
	
	FFT_STEP_SIZE(256), 
	
	FFT_FINGERPRINTS_PER_SECOND_FOR_STORAGE(10), 
	
	/**
	 * The name of the MapDB database location.
	 */
	FFT_MAPDB_DATABASE("fft_panako_db"), 
	
	FFT_LANDMARKS_PER_SECOND_FOR_QUERY(30),
	
	
	
	PCH_FILES("dbs/pch"),
	PCH_SAMPLE_RATE(22050),
	PCH_OVERLAP(1024),
	PCH_SIZE(2048), 
	
	
	/**
	 * The name of the MapDB database location.
	 */
	NFFT_MAPDB_DATABASE("nfft_panako_db"),
	NFFT_SAMPLE_RATE(8000),	
	NFFT_SIZE(512),
	NFFT_STEP_SIZE(256),
	
	
	
	/**
	 * The name of the MapDB database location.
	 */
	NCTEQ_MAPDB_DATABASE("cteq_panako_db"),
	/**
	 * The expected sample rate for the constant q transform.
	 */
	NCTEQ_SAMPLE_RATE(44100),
	/**
	 * Step size in samples for the constant q transform.
	 */
	NCTEQ_STEP_SIZE(1536),
	/**
	 * The minimum pitch, in absolute cents 3383cents is about +-77 Hz.
	 */
	NCTEQ_MIN_FREQ(3700),
	/**
	 * The maximum pitch, in absolute cents. 11533 cents is about +-6392.63 Hz.
	 */
	NCTEQ_MAX_FREQ(12200),
	/**
	 * The number of bins per octave for the constant q transform
	 */
	NCTEQ_BINS_PER_OCTAVE(36),
	/**
	 * The maximum number of event points generated for each second of analyzed
	 * audio to store in the database (Hz).
	 */
	NCTEQ_EVENT_POINTS_PER_SECOND_FOR_STORAGE(8),
	/**
	 * The number of event points per audio second for queries (Hz)
	 */
	NCTEQ_EVENT_POINTS_PER_SECOND_FOR_QUERY(8),
	/**
	 * The maximum delta between two frequency components in one 
	 * fingerprint, in cents
	 */
	NCTEQ_EVENT_POINT_FREQUENCY_DELTA_MAX(1066),
	/**
	 * Defines how much fingerprints can be connected to one 
	 * event point in the spectrum. Increasing this factor improves 
	 * retrieval rate but limits performance and storage needs.
	 */
	NCTEQ_EVENT_POINT_BRANCHING_FOR_STORAGE(1),
	/**
	 * The branching factor for a query needs to be higher to make sure
	 * matches are found, but not too high, so no unneeded hash collisions
	 * are found.
	 */
	NCTEQ_EVENT_POINT_BRANCHING_FOR_QUERY(4),
	/**
	 * The maximum number of hash collisions allowed in storage. It is a
	 * trade-off between recall and response time. More hash collisions
	 * means a larger search time, but more hits. Allowing more collisions
	 * also increases disk space.
	 */
	 NCTEQ_MAX_HASH_COLLISIONS(1000),
	 
	/**
	 * The minimum number of fingerprints that 
	 * need to match between a query and the reference audio. 
	 * The aim is to remove random fingerprint matches.
	 * If this is set too high, some real matches may be discarded. 
	 * Set it too low, and the matching algorithm will spend a lot of time 
	 * checking random matches.  
	 */
	NCTEQ_MINIMUM_MATCHES_THRESHOLD(4),
	/**
	 * The matching algorithm detects a real match if at least this number of fingerprints align. 
	 * If it is set too low, false positives may appear. If it is set too high some real matches
	 * may be ignored. 
	 */
	NCTEQ_MINIMUM_ALIGNED_MATCHES_THRESHOLD(3),
        
        /** TODO
         *  check meaningfulness of default settings
         */
        BALPEAKS_SAMPLE_RATE(44100),
        
        BALPEAKS_FFT_SIZE(1024),
        
        BALPEAKS_FFT_STEP_SIZE(512),
        
        BALPEAKS_MIN_FREQ(150),
        
        BALPEAKS_MAX_FREQ(12000),
        
        BALPEAKS_BANDS(4),
        
        BALPEAKS_LOOKAHEAD(16),
        
        BALPEAKS_MAPDB_DATABASE("balpeaks_panako_db"), 
        
        DEBUG("TRUE"), 
        
        SEGMENTATION_THRESHOLD(25);
	
	String defaultValue;
	private Key(String defaultValue){
		this.defaultValue = defaultValue;
	}
	private Key(int defaultValue){
		this(String.valueOf(defaultValue));
	}
	public String getDefaultValue() {
		return defaultValue;
	}		
}
