package com.burihabwa.cal.avro;

import com.burihabwa.cal.avro.twitter.FastTweet;
import com.burihabwa.cal.avro.twitter.Tweet;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dorian on 10/12/14.
 */
public class FastTweetServer extends HttpServer {
    static class FastTweetImpl implements FastTweet {
        private Logger logger;

        public FastTweetImpl() {
            this.logger = Logger.getLogger("fast-tweet");
        }

        @Override
        public CharSequence send(Tweet tweet) throws AvroRemoteException {
            String message = "";
            message += "@" + tweet.getAuthor().getHandle() + "\n";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            Date date = new Date();
            date.setTime(tweet.getTime());
            message += df.format(date) + "\n";
            message += tweet.getMessage();
            logger.log(Level.INFO, message);
            return message;
        }
    }

    public FastTweetServer(int port) throws IOException {
        super(new SpecificResponder(FastTweet.class, new FastTweetImpl()), port);
    }

    @Override
    public void start() {
        super.start();
    }
}
