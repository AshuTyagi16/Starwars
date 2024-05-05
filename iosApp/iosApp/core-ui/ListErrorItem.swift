//
//  ListErrorItem.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI

struct ListErrorItem: View {
    
    var errorMessage: String
    var onRetryClicked : () -> Void = {}
    
    var body: some View {
        VStack(spacing: 8) {
            
            Text(errorMessage)
                .foregroundColor(Color.white)
                .font(.system(size: 14, weight: .semibold))
                .padding(.top, 12)
            
            Button(action: {
                onRetryClicked()
            }) {
                Text("Retry")
                    .foregroundColor(Color.black)
                    .font(.system(size: 14, weight: .semibold))
                    .padding(.all, 8)
            }
            .background(Color.white)
            .cornerRadius(16)
            .padding(.bottom, 12)
        }
        .frame(maxWidth: .infinity)
    }
}

#Preview {
    ListErrorItem(
        errorMessage: "Some error occurred",
        onRetryClicked: {}
    )
    .background(Color.black)
}
