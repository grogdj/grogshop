//
//
//  Created by Nikita Pahadia on 27/10/2014.
//  Copyright (c) 2014 Nikita Pahadia. All rights reserved.
//

#import "ImageFetchRequest.h"
#import "Constants.h"
#import "AppDelegate.h"

@implementation ImageFetchRequest

#define kStaticURL @"http://grog-restprovider.rhcloud.com/grogshop-server/static/img/public-images/"
/**
 *  Fetches Image from either the local directory or downloads from given URL
 *
 *  @param imageID         ID of the image to be fetched
 *  @param completionBlock call back after fetch completion
 *  @param failureBlock call back if an error was retrieved
 */
- (void)fetchImage:(NSString *)imageName
      completionHandler:(void (^)(UIImage *image))completionBlock
           failureBlock:(void (^)(NSError *error))failureBlock {
    if (imageName != nil) {
        NSFileManager *fileManager = [NSFileManager defaultManager];
        NSString *imagePath = [self getImageLocalPathForName:imageName];
        
        if ([fileManager fileExistsAtPath:imagePath]) {
            //show already downloaded image
            if (completionBlock != nil) {
                completionBlock([UIImage imageWithContentsOfFile:imagePath]);
            }
        } else {
            //downlaod image and save to cache before showing to user
            [self downloadImage:imageName withCompletionHandler:completionBlock failureBlock:failureBlock];
        }
    }
}

- (void)downloadImage:(NSString *)imgName
  withCompletionHandler:(void (^)(UIImage *image))completionBlock
           failureBlock:(void (^)(NSError *error))failureBlock {
    __block NSString *imageName = imgName;
    [[NSFileManager defaultManager] createFileAtPath:[[AppDelegate sharedDelegate] getFilePathForImageIndex:imageName] contents:nil attributes:nil];
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^(void){
        NSString *urlString = [kStaticURL stringByAppendingString:imageName];
        NSError *error = nil;
        NSData* data = [NSData dataWithContentsOfURL:[NSURL URLWithString:urlString] options:NSDataReadingUncached error:&error];
        if (error) {
            [self sendErrorCallback:failureBlock withError:error];
        } else if(!data) {
            [self sendErrorCallback:failureBlock withError:[NSError errorWithDomain:@"Unii" code:NSURLErrorBadServerResponse
                                                                           userInfo:@{NSLocalizedDescriptionKey:@"No Data retrieved"}]];
        } else {
            [self saveImageDataInCache:data withName:imageName];
            
            dispatch_async(dispatch_get_main_queue(), ^{
                if (completionBlock != nil) {
                    completionBlock([UIImage imageWithData:data]);
                }
            });
        }
    });
}

- (void)saveImageDataInCache:(NSData *)data withName:(NSString *)imgName {
    
    NSString *imagePath = [self getImageLocalPathForName:imgName];
    [data writeToFile:imagePath atomically:YES];
    
}


#pragma mark - utility methods
- (NSString *)getImageLocalPathForName:(NSString *)imgName {
    return  [[AppDelegate sharedDelegate] getFilePathForImageIndex:imgName];
}

//- (void)removeItemWithImageName:(NSString *)imgName {
//    [[NSFileManager defaultManager] removeItemAtPath:[[AppDelegate sharedDelegate] getFilePathForImageIndex:imgName] error:nil];
//}

#pragma mark Callbacks

- (void)sendErrorCallback:(void (^)(NSError *error))failureBlock withError:(NSError *)error {
    
    if (failureBlock) {
        dispatch_async(dispatch_get_main_queue(), ^{
            failureBlock(error);
        });
    }
}

@end
