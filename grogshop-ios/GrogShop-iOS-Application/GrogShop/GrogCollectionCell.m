//
//  GrogCollectionCell.m
//  GrogShop
//
//  Created by Nikita Pahadia on 08/02/2015.
//  Copyright (c) 2015 Grogshop. All rights reserved.
//

#import "GrogCollectionCell.h"
#import "ImageFetchRequest.h"
#import "Constants.h"

@implementation GrogCollectionCell

- (instancetype)initWithFrame:(CGRect)frame {
    if (self = [super initWithFrame:frame]) {
        self.backgroundColor = [UIColor clearColor];
        self.icon = [[UIImageView alloc] initWithFrame:self.bounds];
        self.icon.backgroundColor = [UIColor clearColor];
        [self addSubview:self.icon];
        self.icon.contentMode = UIViewContentModeScaleAspectFill;
        self.icon.clipsToBounds = YES;
        
    }
    return self;
}

- (ImageFetchRequest *)request {
    if (!_request) {
        _request = [[ImageFetchRequest alloc] init];
    }
    return _request;
}
- (UIView *)hoverView {
    if (!_hoverView) {
        _hoverView = [[UIView alloc] initWithFrame:self.bounds];
        _hoverView.backgroundColor = [UIColor colorWithRed:134/255.0 green:212/255.0 blue:109/255.0 alpha:1.0];
        _hoverView.hidden = YES;
        _hoverView.alpha = 0.8;
        [self addSubview:_hoverView];
    }
    return _hoverView;
}
- (UILabel *)title {
    if (!_title) {
        _title = [[UILabel alloc] initWithFrame:CGRectMake(5, 5, self.frame.size.width - 10, 20)];
        _title.textColor = [UIColor whiteColor];
        _title.backgroundColor = [UIColor darkGrayColor];
        _title.clipsToBounds=YES;
        _title.layer.cornerRadius = 3;
        [self addSubview:_title];
        _title.textAlignment = NSTextAlignmentCenter;
        _title.hidden = YES;
        _title.adjustsFontSizeToFitWidth = true;
    }
    return _title;
}

- (void)prepareWithTitle:(NSString *)t imagePath:(NSString *)imgPath selected:(BOOL)selected {
    [self.request fetchImage:imgPath completionHandler:^(UIImage *image) {
        self.icon.image = image;
        self.title.text = t;
        
        
    } failureBlock:^(NSError *error) {
        
    }];
    [self toggleHoverView:selected];
}
- (void)toggleHoverView:(BOOL)selected {
    NSLog(@"toggle hover view:%d",selected);
    self.hoverView.hidden = !selected;
    self.title.hidden = !selected;
    [self bringSubviewToFront:self.title];
}

@end
