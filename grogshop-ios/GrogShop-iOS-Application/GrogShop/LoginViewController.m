//
//  ViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "LoginViewController.h"
#import "AppDelegate.h"
#import "Utils.h"
#import "APIRequest.h"
#import "ForgotPasswordViewController.h"

@interface LoginViewController () {
    BOOL isFirst;
}

@end

@implementation LoginViewController

#define Test_email @"abcd@b.com"
#define Test_password @"123456"
#define Test_auth @"ccc0e830-2668-451b-aac1-6e2defa339a3"

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor whiteColor];
    isFirst = YES;
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    self.title = @"Sign In";
    if (isFirst) {
        isFirst = NO;
        
        CGRect frame = self.view.frame;
        
        float textviewWidth = frame.size.width - 50;
        
        _email = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, 50 + 44,textviewWidth, 30)];
        _email.placeholder = @"Email address";
        _email.textAlignment = NSTextAlignmentCenter;
        _email.borderStyle = UITextBorderStyleLine;
        _email.delegate = self;
        [self.view addSubview:_email];
        
        _password = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, CGRectGetMaxY(_email.frame) + 20,textviewWidth, _email.frame.size.height)];
        _password.placeholder = @"Password";
        _password.secureTextEntry = YES;
        _password.textAlignment = NSTextAlignmentCenter;
        _password.borderStyle = UITextBorderStyleLine;
        _password.delegate = self;
        [self.view addSubview:_password];
        
        self.forgotPwd = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        
        _forgotPwd.frame = CGRectMake(_password.frame.origin.x, CGRectGetMaxY(_password.frame) + 20, _password.frame.size.width, 30);
        [_forgotPwd setTitle:@"Forgot your password?" forState:UIControlStateNormal];
        [_forgotPwd addTarget:self action:@selector(forgotPwdClicked) forControlEvents:UIControlEventTouchUpInside];
        [self.view addSubview:_forgotPwd];
        
        self.login = [UIButton buttonWithType:UIButtonTypeRoundedRect];
        
        _login.frame = CGRectMake(_forgotPwd.frame.origin.x, CGRectGetMaxY(_forgotPwd.frame) + 20, _password.frame.size.width, 30);
        [_login setTitle:@"Sign In" forState:UIControlStateNormal];
        [_login addTarget:self action:@selector(startLogin) forControlEvents:UIControlEventTouchUpInside];
        [_login setTitleColor:[UIColor whiteColor] forState:UIControlStateNormal];
        [_login setBackgroundColor:[UIColor colorWithRed:134/255.0 green:212/255.0 blue:109/255.0 alpha:1.0]];
        [self.view addSubview:_login];
        
        
        //_email.text = Test_email;
        //_password.text = Test_password;
        //_login.enabled = NO;
        //_registerUser.enabled = NO;
        AppDelegate *del = [AppDelegate sharedDelegate];
        if (del.emailId) {
            _email.text = del.emailId;
        }
        if (del.auth_token) {
            //take some action
            //[self launchUserSession:false];
            //[[AppDelegate sharedDelegate] launchUserSessionTabBarController];
        }
    }
}

- (NSString *)areTextFieldsValid {
    NSString *retStr = NULL;
    //return an alert string msg if a textfield has invalid entries
    if ([Utils isEmpty:_email.text]) {
        retStr = @"Email address cannot be empty!";
    } else if([Utils isEmpty:_password.text]) {
        retStr = @"Password field cannot be empty!";
    } else if([_password.text length] < 6) {
        retStr = @"Password must be of atleast 6 characters.";
    } else if(![Utils isEmailAddressValid:_email.text]) {
        retStr = @"Please provide a valid email address.";
    }
    return retStr;
}

- (BOOL)checkFields {
    BOOL success = false;
    NSString *value = [self areTextFieldsValid];
    if (!value) { //NULL value indicates no error
        success = true;
        [_email resignFirstResponder];
        [_password resignFirstResponder];
    } else {
        [self showAlert:@"Invalid value!" message:value];
    }
    return success;
}

- (void)startLogin {
    if ([self checkFields]) {
        AppDelegate *del = [AppDelegate sharedDelegate];
        [del startAnimating];
        APIRequest *_request = [[APIRequest alloc] init];
        [_request startLoginRequestWithSuccessBlock:^(id data) {
            NSLog(@"success for login");
            NSLog(@"data:%@",[[NSString alloc] initWithData:(NSData *)data encoding:NSUTF8StringEncoding]);
            if (data) {
                [del stopAnimating];
                NSError *error = nil;
                NSDictionary *rootDictionary = [NSJSONSerialization JSONObjectWithData:data options: NSJSONReadingMutableContainers error: &error];
                if (rootDictionary) {
                    del.emailId = _email.text;
                    NSString *auth = [rootDictionary objectForKey:@"auth_token"];
                    del.auth_token = auth;
                    del.userId = [[rootDictionary objectForKey:@"user_id"] intValue];
                    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
                    [defaults setObject:_email.text forKey:User_email];
                    [defaults setObject:auth forKey:User_auth_token];
                    [defaults setObject:[NSNumber numberWithInt:del.userId] forKey:User_id];
                    [defaults synchronize];
                    
                    [self launchUserSession:[rootDictionary objectForKey:@"firstLogin"]];
                }
            }
            
        } failureBlock:^(NSError *e) {
            NSLog(@"error during login:%@",[e localizedDescription]);
            [del stopAnimating];
            [self showAlert:@"Login Failed!" message:@"Please re-check values provided."];
            
        } email:_email.text password:_password.text];
    }
}

- (void)logout {
    APIRequest *_request = [[APIRequest alloc] init];
    AppDelegate *del = [AppDelegate sharedDelegate];
    [_request startLogoutRequestWithSuccessBlock:^(id rootObj) {
        NSLog(@"success for logout");
        [del stopAnimating];
        NSLog(@"data:%@",[[NSString alloc] initWithData:(NSData *)rootObj encoding:NSUTF8StringEncoding]);
        del.emailId = nil; del.auth_token = nil;
        NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
        [defaults setObject:nil forKey:User_email];
        [defaults setObject:nil forKey:User_auth_token];
        [defaults synchronize];
        
    } failureBlock:^(NSError *e) {
        NSLog(@"error during logout:%@",[e localizedDescription]);
        [del stopAnimating];
        [self showAlert:@"Logout Failed!" message:@"Please re-check values provided."];
        
    } email:del.emailId authToken:del.auth_token];
}

- (void)forgotPwdClicked {
    ForgotPasswordViewController *controller = [[ForgotPasswordViewController alloc] init];
    [self.navigationController pushViewController:controller animated:YES];
}

- (void)showAlert:(NSString *)title message:(NSString *)msg {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Invalid values!" message:msg delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil];
    [alert show];
}

- (void)launchUserSession:(BOOL)firstSession {
    [[AppDelegate sharedDelegate] launchUserSessionTabBarController:firstSession];
}

#pragma mark UITextField delegate

- (BOOL)textFieldShouldBeginEditing:(UITextField *)textField {
    return YES;
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    if (textField == _email) {
        [_password becomeFirstResponder];
    }
    return YES;
}
- (BOOL)textField:(UITextField *)textField shouldChangeCharactersInRange:(NSRange)range replacementString:(NSString *)string {
    if ([string isEqualToString:@" "]) {
        return false;
    }
    return true;
}

- (void)viewDidAppear:(BOOL)animated {
    //[[AppDelegate sharedDelegate] startAnimating];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
