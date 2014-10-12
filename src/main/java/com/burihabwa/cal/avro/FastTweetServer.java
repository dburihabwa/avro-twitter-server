package com.burihabwa.cal.avro;

import com.burihabwa.cal.avro.twitter.FastTweet;
import com.burihabwa.cal.avro.twitter.Tweet;
import com.burihabwa.cal.avro.twitter.User;
import org.apache.avro.AvroRemoteException;
import org.apache.avro.ipc.HttpServer;
import org.apache.avro.ipc.specific.SpecificResponder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dorian on 10/12/14.
 */
public class FastTweetServer extends HttpServer {
    static class FastTweetImpl implements FastTweet {
        private Logger logger;
        private Map<User, List<Tweet>> tweets;

        public FastTweetImpl() {
            this.logger = Logger.getLogger("fast-tweet");
            this.tweets = new HashMap<User, List<Tweet>>();
        }

        @Override
        public CharSequence send(Tweet tweet) throws AvroRemoteException {
            User author = tweet.getAuthor();
            List<Tweet> authorTweets = tweets.get(author);
            if (authorTweets == null) {
                authorTweets = new ArrayList<Tweet>();
            }
            authorTweets.add(tweet);
            this.tweets.put(author, authorTweets);
            String message = "";
            message += "@" + author.getHandle() + "\t";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
            Date date = new Date();
            date.setTime(tweet.getTime());
            message += "(" + df.format(date) + ")\t";
            message += tweet.getMessage();
            logger.log(Level.INFO, message);
            logger.log(Level.INFO, "The database now contains " + tweets.size() + " users and " + authorTweets.size() + " tweets from " +  author.getHandle());
            return message;
        }

        @Override
        public List<Tweet> tweets(User user) throws AvroRemoteException {
            List<Tweet> tweetsFromUser = tweets.get(user);
            int numberOfTweetsFromUser = tweetsFromUser == null ? 0 : tweetsFromUser.size();
            logger.log(Level.INFO, "Returning " + numberOfTweetsFromUser + " tweets from " + user.getHandle());
            return tweetsFromUser;
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
