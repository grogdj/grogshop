//
//
//  Created by Nikita Pahadia on 27/10/2014.
//  Copyright (c) 2014 Nikita Pahadia. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface ImageFetchRequest : NSObject

- (void)fetchImage:(NSString *)imageName
 completionHandler:(void (^)(UIImage *image))completionBlock
      failureBlock:(void (^)(NSError *error))failureBlock;

@end
