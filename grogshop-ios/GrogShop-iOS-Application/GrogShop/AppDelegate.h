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
#import "User.h"

@class AnimatingView;
@class LaunchViewController;
@class LoginViewController;
@class InterestViewController;
@interface AppDelegate : UIResponder <UIApplicationDelegate>

@property (strong, nonatomic) UIWindow *window;
@property (strong, nonatomic) LaunchViewController *launchViewController;
@property (strong, nonatomic) LoginViewController *loginViewController;
@property (strong, nonatomic) InterestViewController *interestViewController;
@property (strong, nonatomic) UINavigationController *navigationController;
@property (strong, nonatomic) UITabBarController *tabBarController;
@property (readonly, strong, nonatomic) NSManagedObjectContext *managedObjectContext;
@property (readonly, strong, nonatomic) NSManagedObjectModel *managedObjectModel;
@property (readonly, strong, nonatomic) NSPersistentStoreCoordinator *persistentStoreCoordinator;
@property (strong, nonatomic) AnimatingView *animatingView;
@property (assign, nonatomic) BOOL firstLogin;
@property (strong, nonatomic) NSString *emailId,*auth_token;
@property (assign, nonatomic) long userId;
@property (strong, nonatomic) User *user;

- (void)saveContext;
- (NSURL *)applicationDocumentsDirectory;
- (void)startAnimating;
- (void)stopAnimating;
+ (AppDelegate *)sharedDelegate;
- (void)launchUserSessionTabBarController:(BOOL)firstLogin;
- (NSString *)getFilePathForImageIndex:(NSString *)imageName;
@end

@interface AnimatingView : UIView
@property(retain,nonatomic) UIActivityIndicatorView *spinningWheel;
@end

