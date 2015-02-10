//
//  SettingsViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "SettingsViewController.h"
#import "APIRequest.h"

@interface SettingsViewController () {
    BOOL isFirstLogin;
}

@end

@implementation SettingsViewController

- (instancetype)init
{
    self = [super init];
    if (self)
    {
        self.title = @"Settings";
    }
    return self;
}
- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    isFirstLogin = true;
}

- (void)viewWillAppear:(BOOL)animated {
    if (isFirstLogin) {
        isFirstLogin = false;
        
        APIRequest *request = [[APIRequest alloc] init];
        AppDelegate *del = [AppDelegate sharedDelegate];
        [del startAnimating];
        [request startGetRequestWithSuccessBlock:^(id rootObj) {
            [del stopAnimating];
            if(rootObj) {
                NSError *error = nil;
                NSDictionary *rootDictionary = [NSJSONSerialization JSONObjectWithData:rootObj options: NSJSONReadingMutableContainers error: &error];
                if (rootDictionary) {
                    NSString *bio,*location,*username;
                    bio = [rootDictionary objectForKey:@"bio"];
                    location = [rootDictionary objectForKey:@"location"];
                    username = [rootDictionary objectForKey:@"username"];
                }
            }
        } failureBlock:^(NSError *e) {
            [del stopAnimating];
        } extension:[NSString stringWithFormat:@"users/%d",del.userId] public:false];
    }
}

@end
