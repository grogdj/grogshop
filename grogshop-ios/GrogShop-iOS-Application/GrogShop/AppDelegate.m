//
//  AppDelegate.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "AppDelegate.h"
#import "LaunchViewController.h"
#import "HomeViewController.h"
#import "AccountViewController.h"
#import "SettingsViewController.h"
#import "Utils.h"

@interface AppDelegate ()

@end

@implementation AppDelegate

+ (AppDelegate *)sharedDelegate {
    return (AppDelegate *) [[UIApplication sharedApplication] delegate];
}

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    [self createTemporaryDirectory];
    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
    self.emailId = [defaults objectForKey:User_email];
    self.auth_token = [defaults objectForKey:User_auth_token];
    self.userId = [[defaults objectForKey:User_id] intValue];
    self.window = [[UIWindow alloc] initWithFrame:[UIScreen mainScreen].bounds];
    self.animatingView = [[AnimatingView alloc] initWithFrame:self.window.bounds];
    
    self.launchViewController = [[LaunchViewController alloc] init];
    self.loginViewNavigationController = [[UINavigationController alloc] initWithRootViewController:self.launchViewController];
    if (![Utils isEmpty:self.emailId] && ![Utils isEmpty:self.auth_token]) {
        [self launchUserSessionTabBarController:true];
    } else {
        self.window.rootViewController = self.loginViewNavigationController;
    }
    [self.window makeKeyAndVisible];
    return YES;
}

- (UITabBarController *)tabBarController
{
    if (!_tabBarController) {
        _tabBarController = [[UITabBarController alloc] init];
        AccountViewController *account = [[AccountViewController alloc] init];
        HomeViewController *home = [[HomeViewController alloc] init];
        home.firstLogin = _firstLogin;
        SettingsViewController *settings = [[SettingsViewController alloc] init];
        _tabBarController.viewControllers = [NSArray arrayWithObjects:account,home,settings, nil];
        _tabBarController.selectedViewController = home;
    }
    return  _tabBarController;
}

- (void)launchUserSessionTabBarController:(BOOL)fl {
    
    _firstLogin = fl;
    if([self.window.rootViewController isEqual:self.loginViewNavigationController]) {
        [self.loginViewNavigationController pushViewController:self.tabBarController animated:YES];
        self.loginViewNavigationController.navigationBarHidden = YES;
    } else {
        self.window.rootViewController = self.tabBarController;
    }
}

- (void)startAnimating {
    [self.window addSubview:_animatingView];
    [self.window bringSubviewToFront:_animatingView];
}
- (void)stopAnimating {
    [_animatingView removeFromSuperview];
}

- (NSString *)cacheDirPath {
    NSFileManager *mgr = [NSFileManager defaultManager];
    // Create a unique directory in the system caches directory
    NSArray *docPaths = [mgr URLsForDirectory:NSCachesDirectory inDomains:NSUserDomainMask];
    NSString *docPath = [(NSURL *)[docPaths objectAtIndex:0] path];
    docPath = [docPath stringByAppendingPathComponent:kCacheDirName];
    return docPath;
}
- (NSString *)getFilePathForImageIndex:(NSString *)imageName {

    return [[self cacheDirPath] stringByAppendingPathComponent:[NSString stringWithFormat:@"%@",imageName]];
}

- (void) createTemporaryDirectory {
    NSString *docPath = [self cacheDirPath];
    NSFileManager *mgr = [NSFileManager defaultManager];
    BOOL isDir;
    if (![mgr fileExistsAtPath:docPath isDirectory:&isDir]) {
        [mgr createDirectoryAtPath:docPath withIntermediateDirectories:NO attributes:nil error:nil];
    }
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
    // Saves changes in the application's managed object context before the application terminates.
    [self saveContext];
}

#pragma mark - Core Data stack

@synthesize managedObjectContext = _managedObjectContext;
@synthesize managedObjectModel = _managedObjectModel;
@synthesize persistentStoreCoordinator = _persistentStoreCoordinator;

- (NSURL *)applicationDocumentsDirectory {
    // The directory the application uses to store the Core Data store file. This code uses a directory named "com.grogshop.iosapp.GrogShop" in the application's documents directory.
    return [[[NSFileManager defaultManager] URLsForDirectory:NSDocumentDirectory inDomains:NSUserDomainMask] lastObject];
}

- (NSManagedObjectModel *)managedObjectModel {
    // The managed object model for the application. It is a fatal error for the application not to be able to find and load its model.
    if (_managedObjectModel != nil) {
        return _managedObjectModel;
    }
    NSURL *modelURL = [[NSBundle mainBundle] URLForResource:@"GrogShop" withExtension:@"momd"];
    _managedObjectModel = [[NSManagedObjectModel alloc] initWithContentsOfURL:modelURL];
    return _managedObjectModel;
}

- (NSPersistentStoreCoordinator *)persistentStoreCoordinator {
    // The persistent store coordinator for the application. This implementation creates and return a coordinator, having added the store for the application to it.
    if (_persistentStoreCoordinator != nil) {
        return _persistentStoreCoordinator;
    }
    
    // Create the coordinator and store
    
    _persistentStoreCoordinator = [[NSPersistentStoreCoordinator alloc] initWithManagedObjectModel:[self managedObjectModel]];
    NSURL *storeURL = [[self applicationDocumentsDirectory] URLByAppendingPathComponent:@"GrogShop.sqlite"];
    NSError *error = nil;
    NSString *failureReason = @"There was an error creating or loading the application's saved data.";
    if (![_persistentStoreCoordinator addPersistentStoreWithType:NSSQLiteStoreType configuration:nil URL:storeURL options:nil error:&error]) {
        // Report any error we got.
        NSMutableDictionary *dict = [NSMutableDictionary dictionary];
        dict[NSLocalizedDescriptionKey] = @"Failed to initialize the application's saved data";
        dict[NSLocalizedFailureReasonErrorKey] = failureReason;
        dict[NSUnderlyingErrorKey] = error;
        error = [NSError errorWithDomain:@"YOUR_ERROR_DOMAIN" code:9999 userInfo:dict];
        // Replace this with code to handle the error appropriately.
        // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
        NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
        abort();
    }
    
    return _persistentStoreCoordinator;
}


- (NSManagedObjectContext *)managedObjectContext {
    // Returns the managed object context for the application (which is already bound to the persistent store coordinator for the application.)
    if (_managedObjectContext != nil) {
        return _managedObjectContext;
    }
    
    NSPersistentStoreCoordinator *coordinator = [self persistentStoreCoordinator];
    if (!coordinator) {
        return nil;
    }
    _managedObjectContext = [[NSManagedObjectContext alloc] init];
    [_managedObjectContext setPersistentStoreCoordinator:coordinator];
    return _managedObjectContext;
}

#pragma mark - Core Data Saving support

- (void)saveContext {
    NSManagedObjectContext *managedObjectContext = self.managedObjectContext;
    if (managedObjectContext != nil) {
        NSError *error = nil;
        if ([managedObjectContext hasChanges] && ![managedObjectContext save:&error]) {
            // Replace this implementation with code to handle the error appropriately.
            // abort() causes the application to generate a crash log and terminate. You should not use this function in a shipping application, although it may be useful during development.
            NSLog(@"Unresolved error %@, %@", error, [error userInfo]);
            abort();
        }
    }
}

@end

@implementation AnimatingView

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    
    _spinningWheel = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleWhite];
    [self addSubview:_spinningWheel];
    _spinningWheel.center = self.center;
    self.backgroundColor = [UIColor blackColor];
    self.alpha = 0.3;
    
    return self;
}
- (void)willMoveToSuperview:(UIView *)newSuperview {
    if (newSuperview) {
        [_spinningWheel startAnimating];
        
    } else {
        [_spinningWheel stopAnimating];
    }
}

@end
