//
//  SettingsViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "SettingsViewController.h"
#import "APIRequest.h"
#import <QuartzCore/QuartzCore.h>

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
        [self prepareView];
        APIRequest *request = [[APIRequest alloc] init];
        AppDelegate *del = [AppDelegate sharedDelegate];
        [del startAnimating];
        [request startGetRequestWithSuccessBlock:^(id rootObj) {
            [del stopAnimating];
            if(rootObj) {
                NSError *error = nil;
                id root = [NSJSONSerialization JSONObjectWithData:rootObj options: NSJSONReadingMutableContainers error: &error];
                if (root && [root isKindOfClass:[NSDictionary class]]) {
                    NSDictionary *rootDictionary = (NSDictionary *)root;
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

- (void)prepareView {
    CGRect frame = self.view.frame;
    
    float textviewWidth = frame.size.width - 50;
    
    _username = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, 40 + 44,textviewWidth, 30)];
    _username.placeholder = @"Enter your name";
    _username.textAlignment = NSTextAlignmentCenter;
    _username.borderStyle = UITextBorderStyleLine;
    _username.delegate = self;
    [self.view addSubview:_username];
    
    _location = [[UITextField alloc] initWithFrame:CGRectMake(_username.frame.origin.x, CGRectGetMaxY(_username.frame) + 20,textviewWidth, _username.frame.size.height)];
    _location.placeholder = @"Your Location";
    _location.textAlignment = NSTextAlignmentCenter;
    _location.borderStyle = UITextBorderStyleLine;
    _location.delegate = self;
    [self.view addSubview:_location];
    
    _bio = [[UITextField alloc] initWithFrame:CGRectMake(_username.frame.origin.x, CGRectGetMaxY(_location.frame) + 20, textviewWidth, _username.frame.size.height * 3)];
    _bio.placeholder = @"Type a few words about yourself";
    _bio.textAlignment = NSTextAlignmentCenter;
    _bio.borderStyle = UITextBorderStyleLine;
    _bio.delegate = self;
    
    [self.view addSubview:_bio];
    
    self.save = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    _save.frame = CGRectMake(_bio.frame.origin.x, CGRectGetMaxY(_bio.frame) + 20, _bio.frame.size.width, 30);
    [_save setTitle:@"Save" forState:UIControlStateNormal];
    [_save addTarget:self action:@selector(saveProfile) forControlEvents:UIControlEventTouchUpInside];
    [_save setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
    [_save setBackgroundColor:[UIColor colorWithRed:123/255.0 green:169/255.0 blue:208/255.0 alpha:1.0]];
    [self.view addSubview:_save];
    
    if (_showHomeBarButton) {
        self.navigationItem.rightBarButtonItem = [[UIBarButtonItem alloc] initWithTitle:@"Home" style:UIBarButtonItemStylePlain target:self action:@selector(saveProfile)];
    }
}

- (void)saveProfile {
    APIRequest *request = [[APIRequest alloc] init];
    [request startUpdateUserProfileRequestWithSuccessBlock:^(id rootObj) {
    } failureBlock:^(NSError *e) {
    } name:_username.text location:_location.text bio:_bio.text];
    
    [[AppDelegate sharedDelegate] launchUserSessionTabBarController:true];
}

@end
