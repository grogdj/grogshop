//
//  APIRequest.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "APIRequest.h"
#import "Utils.h"

@implementation APIRequest

#define kBaseURL @"http://grog-restprovider.rhcloud.com/grogshop-server/rest/"
#define kDomain @"Grogshop"

- (void)sendSuccessCallback:(id)rootObject successBlock:(void (^)(id rootObj))success {
    if (success != nil) {
        dispatch_async(dispatch_get_main_queue(), ^{
            success(rootObject);
        });
    }
}

- (void)sendErrorCallback:(NSError *)error failureBlock:(void (^)(NSError *e))failed {
    if (failed != nil) {
        dispatch_async(dispatch_get_main_queue(), ^{
            failed(error);
        });
        
    }
}

- (void)checkAndProvideCallback:(NSInteger)statusCode dataReceived:(NSData *)data error:(NSError *)error successBlock:(void (^)(id rootObj))success
                   failureBlock:(void (^)(NSError *e))failed {
    if (error) {
        [self sendErrorCallback:error failureBlock:failed];
    } else if( statusCode != 200 && statusCode != 204) {
        //response not generated correctly
        [self sendErrorCallback:[NSError errorWithDomain:kDomain code:NSURLErrorBadServerResponse userInfo:@{NSLocalizedDescriptionKey: [NSString stringWithFormat:@"Received status code: %ld",(long)statusCode]}] failureBlock:failed];
    } else if(data == nil) {
        [self sendErrorCallback:[NSError errorWithDomain:kDomain code:NSURLErrorBadServerResponse userInfo:@{NSLocalizedDescriptionKey:@"No data received!"}] failureBlock:failed];
    }
    else {
        //NSLog(@"data recv:%@",[[NSString alloc] initWithData:data encoding:NSUTF8StringEncoding]);
        [self sendSuccessCallback:data successBlock:success];
    }
}

- (void)startAPIRequestWithSuccessBlock:(void (^)(id rootObj))success
                           failureBlock:(void (^)(NSError *e))failed urlRequest:(NSMutableURLRequest *)urlRequest {
    
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        NSError *error = nil;
        NSURLResponse *response = nil;
        NSData* data = [NSURLConnection sendSynchronousRequest:urlRequest returningResponse:&response error:&error];
        long statusCode = [(NSHTTPURLResponse *)response statusCode];
        [self checkAndProvideCallback:statusCode dataReceived:data error:error successBlock:success failureBlock:failed];
    });
    
}

- (void)startRegistrationRequestWithSuccessBlock:(void (^)(id rootObj))success
                                    failureBlock:(void (^)(NSError *e))failed email:(NSString *)email password:(NSString *)password {
    
    NSString *url = [NSString stringWithFormat:@"%@%@",kBaseURL,@"auth/register"];
    NSMutableURLRequest *urlRequest = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    [urlRequest setHTTPMethod:@"POST"];
    NSString *stringToPost = [NSString stringWithFormat:@"password=%@&email=%@",[Utils getURLEncoded:password],[Utils getURLEncoded:email]];
    NSData *dataToPost = [stringToPost dataUsingEncoding:NSUTF8StringEncoding];
    [urlRequest setHTTPBody:dataToPost];
    [urlRequest setValue:[NSString stringWithFormat:@"%ld",(long)[dataToPost length]] forHTTPHeaderField:@"Content-Length"];
    [urlRequest setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [self startAPIRequestWithSuccessBlock:success failureBlock:failed urlRequest:urlRequest];
}

- (void)startLoginRequestWithSuccessBlock:(void (^)(id rootObj))success
                             failureBlock:(void (^)(NSError *e))failed email:(NSString *)email password:(NSString *)password {
    NSString *url = [NSString stringWithFormat:@"%@%@",kBaseURL,@"auth/login"];
    NSMutableURLRequest *urlRequest = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    [urlRequest setHTTPMethod:@"POST"];
    NSString *stringToPost = [NSString stringWithFormat:@"password=%@&email=%@",[Utils getURLEncoded:password],[Utils getURLEncoded:email]];
    NSData *dataToPost = [stringToPost dataUsingEncoding:NSUTF8StringEncoding];
    [urlRequest setHTTPBody:dataToPost];
    [urlRequest setValue:[NSString stringWithFormat:@"%ld",(long)[dataToPost length]] forHTTPHeaderField:@"Content-Length"];
    [urlRequest setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [urlRequest setValue:[NSString stringWithFormat:@"webkey:%@",email] forHTTPHeaderField:@"service_key"];
    
    [self startAPIRequestWithSuccessBlock:success failureBlock:failed urlRequest:urlRequest];
}
- (void)startLogoutRequestWithSuccessBlock:(void (^)(id rootObj))success
                             failureBlock:(void (^)(NSError *e))failed email:(NSString *)email authToken:(NSString *)token {
    NSString *url = [NSString stringWithFormat:@"%@%@",kBaseURL,@"auth/logout"];
    NSMutableURLRequest *urlRequest = [[NSMutableURLRequest alloc] initWithURL:[NSURL URLWithString:url]];
    [urlRequest setHTTPMethod:@"POST"];
//    NSString *stringToPost = [NSString stringWithFormat:@"password=%@&email=%@",[Utils getURLEncoded:password],[Utils getURLEncoded:email]];
//    NSData *dataToPost = [stringToPost dataUsingEncoding:NSUTF8StringEncoding];
//    [urlRequest setHTTPBody:dataToPost];
//    [urlRequest setValue:[NSString stringWithFormat:@"%ld",(long)[dataToPost length]] forHTTPHeaderField:@"Content-Length"];
    [urlRequest setValue:@"application/x-www-form-urlencoded" forHTTPHeaderField:@"Content-Type"];
    [urlRequest setValue:[NSString stringWithFormat:@"webkey:%@",email] forHTTPHeaderField:@"service_key"];
    [urlRequest setValue:[NSString stringWithFormat:@"%@",token] forHTTPHeaderField:@"auth_token"];
    [self startAPIRequestWithSuccessBlock:success failureBlock:failed urlRequest:urlRequest];
}

@end
