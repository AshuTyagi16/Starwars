//
//  ListLoadingItem.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI

struct ListLoadingItem: View {
    
    @State private var progressForDefaultSector: CGFloat = 0.0
    
    private let timer = Timer.publish(every: 1 / 15, on: .main, in: .common).autoconnect()
    
    var body: some View {
        VStack(alignment: .center, spacing: 12) {
            Text("Loading...")
                .foregroundColor(.white)
            
            ProgressView()
            .frame(width: 20.0, height: 20.0)
            .foregroundColor(.white)
            .padding(.top, 4)
            
        }
        .frame(maxWidth: .infinity, maxHeight: 80, alignment: .center)
    }
}

#Preview {
    ListLoadingItem()
        .background(Color.black)
}
