#!/usr/bin/env python

import sys
import httplib
import time

import avro.ipc as ipc
import avro.protocol as protocol

PROTOCOL = protocol.parse(open("../avro/twitter_proto.avpr").read())

server_addr = ('localhost', 42000)

def create_user(handle):
    """ Create a new user """
    user = dict()
    user['handle'] = handle
    return user
    
def create_tweet(author, message):
    """ Create a new tweet """
    tweet = dict()
    tweet['author'] = author
    tweet['time'] = int(round(time.time() * 1000))
    tweet['message'] = message
    return tweet

def send_tweet(handle = "anonymous", message = "had nothing to say"):
    """ Send a tweet as a user """
    client = ipc.HTTPTransceiver(server_addr[0], server_addr[1])
    requestor = ipc.Requestor(PROTOCOL, client)
    author = create_user(handle)
    tweet = create_tweet(author, message)
    params = dict()
    params['tweet'] = tweet
    result = requestor.request('send', params)
    client.close()
    return result

def get_tweets(handle):
    """ Return a list of tweets from a specific user """
    client = ipc.HTTPTransceiver(server_addr[0], server_addr[1])
    requestor = ipc.Requestor(PROTOCOL, client)
    user = create_user(handle)
    params = dict()
    params['user'] = user
    result = requestor.request('tweets', params)
    client.close()
    return result

if __name__ == '__main__':
    usage = "Usage : tweet | list"
    if len(sys.argv) != 2:
        print(usage)
    elif sys.argv[1] == "tweet":
        handle = raw_input("Please enter your handle :\t")
        message = raw_input("Please enter your message :\t")
        print send_tweet(handle, message)
    elif sys.argv[1] == "list":
        handle = raw_input("Please the handle of the user whose tweets you would like to read :\t")
        tweets = get_tweets(handle)
        for tweet in tweets:
            print("@" + tweet['author']['handle'] + "\t(" + str(tweet['time']) + ")\t" + tweet['message'])
    else:
        print("Command " + sys.argv[1] + " not supported")
        print(usage)
    
        
