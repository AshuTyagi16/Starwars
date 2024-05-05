//
//  PlanetListScreen.swift
//  iosApp
//
//  Created by Ashu Tyagi on 05/05/24.
//

import Foundation
import SwiftUI
import FlowStacks
import shared

struct PlanetListScreen: View {
    
    @EnvironmentObject private var navigator: FlowNavigator<AppRoute>
    
    private var pagingHelper = SwiftUiPagingHelper<Planet>()
    
    @State private var viewModel : PlanetListScreenModel? = nil
    
    @State private var hasNextPage: Bool = false
    
    @State private var errorMessage: String? = nil
    
    @State private var showLoadingPlaceholder: Bool = false
    
    @State private var items: [Planet] = []
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            List {
                ForEach(items, id: \.uid) { item in
                    Text(item.name)
                        .foregroundColor(.white)
                        .padding(.vertical, 12)
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                }
                
                if(showLoadingPlaceholder) {
                    ProgressView()
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                }
                
                if(hasNextPage && errorMessage == nil && !items.isEmpty) {
                    getLoadindView()
                        .onAppear{
                            pagingHelper.loadNextPage()
                        }
                }
                if(errorMessage != nil) {
                    getErrorView(
                        errorMessage: errorMessage!,
                        onRetryClicked: {
                            pagingHelper.retry()
                            self.errorMessage = nil
                        }
                    )
                }
            }
            .listStyle(.plain)
            
            if(items.isEmpty){
                Spacer()
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black.opacity(0.92)).edgesIgnoringSafeArea(.bottom)
        .task {
            let viewModel = SharedModuleDependencies.shared.planetListScreenModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    for await pagingData in viewModel.pager {
                        try? await skie(pagingHelper).submitData(pagingData: pagingData)
                    }
                },
                onCancel: {
                    viewModel.onCleared()
                    DispatchQueue.main.async {
                        self.viewModel = nil
                    }
                }
            )
        }
        .task {
            for await _ in pagingHelper.onPagesUpdatedFlow {
                pagingHelper.getItems().forEach { item in
                    if(self.items.contains(item) == false){
                        self.items.append(item)
                    }
                }
            }
        }
        .task {
            for await loadState in pagingHelper.loadStateFlow {
                switch onEnum(of: loadState.append) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    break
                case .loading(_):
                    break
                case .notLoading(let notLoadingState):
                    self.hasNextPage = !notLoadingState.endOfPaginationReached
                    break
                }
                
                switch onEnum(of: loadState.refresh) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    self.showLoadingPlaceholder = false
                    break
                case .loading(_):
                    self.showLoadingPlaceholder = true
                    break
                case .notLoading(_):
                    self.showLoadingPlaceholder = false
                    break
                }
            }
        }
    }
    
    @ViewBuilder
    private func getLoadindView() -> some View {
        HStack {
            ListLoadingItem()
        }
        .listRowInsets(EdgeInsets())
        .listRowSeparator(.hidden, edges: .all)
        .listRowBackground(Color.clear)
    }

    @ViewBuilder
    private func getErrorView(
        errorMessage: String,
        onRetryClicked: @escaping () -> Void
    ) -> some View {
        HStack {
            ListErrorItem(
                errorMessage: errorMessage,
                onRetryClicked: onRetryClicked
            )
        }
        .listRowInsets(EdgeInsets())
        .listRowSeparator(.hidden, edges: .all)
        .listRowBackground(Color.clear)
    }
    
}
