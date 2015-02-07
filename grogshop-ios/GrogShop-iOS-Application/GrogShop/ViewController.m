//
//  ViewController.m
//  GrogShop
//
//  Created by Nikita Pahadia on 07/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "ViewController.h"
#import "AppDelegate.h"
#import "Utils.h"
#import "APIRequest.h"

@interface ViewController ()

@end

@implementation ViewController

#define Test_email @"abcd@b.com"
#define Test_password @"123456"
#define Test_auth @"ccc0e830-2668-451b-aac1-6e2defa339a3"

- (void)viewDidLoad {
    [super viewDidLoad];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    CGRect frame = self.view.frame;

    float textviewWidth = frame.size.width - 50;

    _email = [[UITextField alloc] initWithFrame:CGRectMake(frame.size.width/2 - textviewWidth/2, 50,textviewWidth, 30)];
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
    
    self.login = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    self.registerUser = [UIButton buttonWithType:UIButtonTypeRoundedRect];
    
    _login.frame = CGRectMake(frame.size.width/2 - textviewWidth/2, _password.frame.size.height + _password.frame.origin.y + 20, 50, 20);
    [_login setTitle:@"Login" forState:UIControlStateNormal];
    [_login addTarget:self action:@selector(startLogin) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_login];
    
    _registerUser.frame = CGRectMake(CGRectGetMaxX(_password.frame) - 80, _login.frame.origin.y, 80, 20);
    [_registerUser setTitle:@"Register" forState:UIControlStateNormal];
    [_registerUser addTarget:self action:@selector(startRegistration) forControlEvents:UIControlEventTouchUpInside];
    [self.view addSubview:_registerUser];
    
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
        [self launchUserSession:false];
    }
    self.title = @"Grogshop";
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
                    NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
                    [defaults setObject:_email.text forKey:User_email];
                    [defaults setObject:auth forKey:User_auth_token];
                    [defaults synchronize];
                    
                    [self launchUserSession:true];
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

- (void)startRegistration {
    if ([self checkFields]) {
        AppDelegate *del = [AppDelegate sharedDelegate];
        [del startAnimating];
        APIRequest *_request = [[APIRequest alloc] init];
        [_request startRegistrationRequestWithSuccessBlock:^(id rootObj) {
            NSLog(@"success for registration");
            [del stopAnimating];
            NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
            [defaults setObject:_email.text forKey:User_email];
            [defaults synchronize];
        } failureBlock:^(NSError *e) {
            NSLog(@"error during registration:%@",[e localizedDescription]);
            [del stopAnimating];
            [self showAlert:@"Registration Failed!" message:@"Please re-check values provided."];
            
        } email:_email.text password:_password.text];
    }
}

- (void)showAlert:(NSString *)title message:(NSString *)msg {
    UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Invalid values!" message:msg delegate:self cancelButtonTitle:@"OK" otherButtonTitles: nil];
    [alert show];
}

- (void)launchUserSession:(BOOL)animated {
    
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
