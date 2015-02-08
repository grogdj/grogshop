//
//  AppDelegate.h
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <CoreData/CoreData.h>
#import "Constants.h"


@class AnimatingView;
@class LaunchViewController;
@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) LaunchViewController *launchViewController;
@property (strong, nonatomic) UINavigationController *loginViewNavigationController;
@property (strong, nonatomic) UITabBarController *tabBarController;
@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;
@property (strong, nonatomic) AnimatingView *animatingView;

@property(strong,nonatomic) NSString *emailId,*auth_token;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;
- (void)startAnimating;
- (void)stopAnimating;
+ (AppDelegate *)sharedDelegate;
- (void)launchUserSessionTabBarController;
@end

@interface AnimatingView : UIView
@property(retain,nonatomic) UIActivityIndicatorView *spinningWheel;
@end

