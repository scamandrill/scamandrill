package io.github.scamandrill.client

import io.github.scamandrill.models.JsScalar.{JsScalarBoolean, JsScalarNumber, JsScalarString}
import play.api.libs.ws.WSClient
import io.github.scamandrill.models._

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

class MandrillClient(
  val ws: WSClient,
  val key: APIKey = APIKey(),
  val onShutdown: () => Future[Unit] = () => Future.successful(())
)(implicit val ec: ExecutionContext) extends ScamandrillSendReceive with MandrillClientProvider {
  import MandrillClient.Endpoints._

  /**
    * If the WSClient was created by Scamandrill, this will shut down the scamandrill actor system and client.
    * If the WSClient was not created by Scamandrill, this will close the client.
    *
    * @see [[io.github.scamandrill.client.ScamandrillSendReceive]]
    */
  def shutdownSystem(): Future[Unit] = shutdown()

  /////////////////////////////////////////////////////////////
  //USER calls https://mandrillapp.com/api/docs/users.JSON.html
  /////////////////////////////////////////////////////////////

  /**
    * Validate an API key and respond to a ping
    *
    * @return - the string "PONG!" if successful
    */
  def usersPing: Future[Try[MPingResponse]] = {
    executeQuery[MVoid, MPingResponse](ping, MVoid())
  }

  /**
    * Validate an API key and respond to a ping (anal JSON parser version)
    *
    * @return - the string "PONG!" if successful
    */
  def usersPing2: Future[Try[MPingResponse]] = {
    executeQuery[MVoid, MPingResponse](ping2, MVoid())
  }

  /**
    * Return the senders that have tried to use this account, both verified and unverified
    *
    * @return the senders that have tried to use this account, both verified and unverified
    */
  def usersSenders: Future[Try[List[MSenderDataResponse]]] = {
    executeQuery[MVoid, List[MSenderDataResponse]](senders, MVoid())
  }

  /**
    * Return the information about the API-connected user
    *
    * @return the information about the API-connected user
    */
  def usersInfo: Future[Try[MInfoResponse]] = {
    executeQuery[MVoid, MInfoResponse](info, MVoid())
  }

  ////////////////////////////////////////////////////////////////////
  //MESSAGES calls https://mandrillapp.com/api/docs/messages.JSON.html
  ////////////////////////////////////////////////////////////////////

  /**
    * Send a new transactional message through Mandrill
    *
    * @param msg - the message to send
    * @return - an of structs for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSend(msg: MSendMessage): Future[Try[List[MSendResponse]]] = {
    executeQuery[MSendMessage, List[MSendResponse]](send, msg)
  }

  /**
    * Send a new transactional message through Mandrill using a template
    *
    * @param msg - the message to send
    * @return - an of structs for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSendTemplate(msg: MSendTemplateMessage): Future[Try[List[MSendResponse]]] = {
    executeQuery[MSendTemplateMessage,List[MSendResponse]](sendTemplate, msg)
  }

  /**
    * Search the content of recently sent messages and optionally narrow by date range, tags and senders.
    * This method may be called up to 20 times per minute. If you need the data more often, you can use
    * /messages/info.json to get the information for a single message, or webhooks to push activity to
    * your own application for querying.
    *
    * @param q - the search values
    * @return an array of information for a single matching message
    */
  def messagesSearch(q: MSearch): Future[Try[List[MSearchResponse]]] = {
    executeQuery[MSearch, List[MSearchResponse]](search, q)
  }


  /**
    * Search the content of recently sent messages and return the aggregated hourly stats for matching messages
    *
    * @param q - the search values
    * @return the history information
    */
  def messagesSearchTimeSeries(q: MSearchTimeSeries): Future[Try[List[MTimeSeriesResponse]]] = {
    executeQuery[MSearchTimeSeries, List[MTimeSeriesResponse]](searchTimeS, q)
  }

  /**
    * Get the information for a single recently sent message
    *
    * @param q - the message info (containing unique id)
    * @return the information for the message
    */
  def messagesInfo(q: MMessageInfo): Future[Try[MMessageInfoResponse]] = {
    executeQuery[MMessageInfo, MMessageInfoResponse](msginfo, q)
  }

  /**
    * Get the full content of a recently sent message
    *
    * @param q - the message info (containing unique id)
    * @return the content of the message
    */
  def messagesContent(q: MMessageInfo): Future[Try[MContentResponse]] = {
    executeQuery[MMessageInfo, MContentResponse](content, q)
  }

  /**
    * Parse the full MIME document for an email message, returning the content of the message broken into its constituent pieces
    *
    * @param raw - the full MIME document of an email message
    * @return the parsed message
    */
  def messagesParse(raw: MParse): Future[Try[MParseResponse]] = {
    executeQuery[MParse,MParseResponse](parse, raw)
  }

  /**
    * Take a raw MIME document for a message, and send it exactly as if it were sent through Mandrill's SMTP servers
    *
    * @param raw - the full MIME document of an email message
    * @return an array for each recipient containing the key "email" with the email address and "status" as either "sent", "queued", or "rejected"
    */
  def messagesSendRaw(raw: MSendRaw): Future[Try[List[MSendResponse]]] = {
    executeQuery[MSendRaw, List[MSendResponse]](sendraw, raw)
  }

  /**
    * Queries your scheduled emails by sender or recipient, or both.
    *
    * @param sc - the recipient address to restrict results to
    * @return a list of up to 1000 scheduled emails
    */
  def messagesListSchedule(sc: MListSchedule): Future[Try[List[MScheduleResponse]]] = {
    executeQuery[MListSchedule, List[MScheduleResponse]](listSchedule, sc)
  }

  /**
    * Cancels a scheduled email
    *
    * @param sc - the scheduled mail
    * @return information about the scheduled email that was cancelled.
    */
  def messagesCancelSchedule(sc: MCancelSchedule): Future[Try[MScheduleResponse]] = {
    executeQuery[MCancelSchedule, MScheduleResponse](cancelSchedule, sc)
  }

  /**
    * Reschedules a scheduled email.
    *
    * @param sc - the mail to reschedule
    * @return information about the scheduled email that was rescheduled.
    */
  def messagesReschedule(sc: MReSchedule): Future[Try[MScheduleResponse]] = {
    executeQuery[MReSchedule, MScheduleResponse](reschedule, sc)
  }

  ////////////////////////////////////////////////////////////
  //TAGS calls https://mandrillapp.com/api/docs/tags.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Return all of the user-defined key information
    *
    * @return all of the user-defined key information
    */
  def tagList(): Future[Try[List[MTagResponse]]] = {
    executeQuery[MVoid, List[MTagResponse]](taglist, MVoid())
  }

  /**
    * Deletes a tag permanently. Deleting a tag removes the tag from any messages that have been sent, and also deletes the tag's stats.
    * There is no way to undo this operation, so use it carefully.
    *
    * @param tag - the existing tag info
    * @return the tag that was deleted
    */
  def tagDelete(tag: MTagRequest): Future[Try[MTagResponse]] = {
    executeQuery[MTagRequest, MTagResponse](tagdelete, tag)
  }

  /**
    * Return more detailed information about a single tag, including aggregates of recent stats
    *
    * @param tag - the existing tag info
    * @return the tag asked
    */
  def tagInfo(tag: MTagRequest): Future[Try[MTagInfoResponse]] = {
    executeQuery[MTagRequest, MTagInfoResponse](taginfo, tag)
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a tag
    *
    * @param tag - the existing tag info
    * @return the recent history (hourly stats for the last 30 days) for a tag
    */
  def tagTimeSeries(tag: MTagRequest): Future[Try[List[MTimeSeriesResponse]]] = {
    executeQuery[MTagRequest, List[MTimeSeriesResponse]](tagtimeseries, tag)
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for all tags
    *
    * @return the recent history (hourly stats for the last 30 days) for all tags
    */
  def tagAllTimeSeries(): Future[Try[List[MTimeSeriesResponse]]] = {
    executeQuery[MVoid, List[MTimeSeriesResponse]](tagalltime, MVoid())
  }

  /////////////////////////////////////////////////////////////////
  //REJECT calls https://mandrillapp.com/api/docs/rejects.JSON.html
  /////////////////////////////////////////////////////////////////

  /**
    * Adds an email to your email rejection blacklist. Addresses that you add manually will never expire and there is no
    * reputation penalty for removing them from your blacklist. Attempting to blacklist an address that has been
    * whitelisted will have no effect.
    *
    * @param reject - info about the mail to blacklist
    * @return an object containing the address and the result of the operation
    */
  def rejectAdd(reject: MRejectAdd): Future[Try[MRejectAddResponse]] = {
    executeQuery[MRejectAdd, MRejectAddResponse](rejadd, reject)
  }

  /**
    * Deletes an email rejection. There is no limit to how many rejections you can remove from your blacklist,
    * but keep in mind that each deletion has an affect on your reputation.
    *
    * @param delete - the mail to delete from the blacklist
    * @return - an object containing the address and whether the deletion succeeded.
    */
  def rejectDelete(delete: MRejectDelete): Future[Try[MRejectDeleteResponse]] = {
    executeQuery[MRejectDelete, MRejectDeleteResponse](rejdelete, delete)
  }

  /**
    * Retrieves your email rejection blacklist. You can provide an email address to limit the results.
    * Returns up to 1000 results. By default, entries that have expired are excluded from the results;
    * set include_expired to true to include them.
    *
    * @param reject - information about the list of mails to retrieve
    * @return up to 1000 results
    */
  def rejectList(reject: MRejectList): Future[Try[List[MRejectListResponse]]] = {
    executeQuery[MRejectList, List[MRejectListResponse]](rejlist, reject)
  }

  ///////////////////////////////////////////////////////////////////////
  //WHITELIST calls https://mandrillapp.com/api/docs/whitelists.JSON.html
  ///////////////////////////////////////////////////////////////////////

  /**
    * Adds an email to your email rejection whitelist. If the address is currently on your blacklist,
    * that blacklist entry will be removed automatically.
    *
    * @param mail - the mail to be added to the whitelist
    * @return an object containing the address and the result of the operation
    */
  def whitelistAdd(mail: MWhitelistAdd): Future[Try[MWhitelistAddResponse]] = {
    executeQuery[MWhitelistAdd, MWhitelistAddResponse](wlistadd, mail)
  }

  /**
    * Removes an email address from the whitelist.
    *
    * @param mail - the mail to be removed from the whitelist
    * @return a status object containing the address and whether the deletion succeeded
    */
  def whitelistDelete(mail: MWhitelist): Future[Try[MWhitelistDeleteResponse]] = {
    executeQuery[MWhitelist, MWhitelistDeleteResponse](wlistdelete, mail)
  }

  /**
    * Retrieves your email rejection whitelist. You can provide an email address or search prefix to limit the results.
    *
    * @param mail - the list of mails to be returned
    * @return up to 1000 results
    */
  def whitelistList(mail: MWhitelist): Future[Try[List[MWhitelistListResponse]]] = {
    executeQuery[MWhitelist, List[MWhitelistListResponse]](wlistlist, mail)
  }

  //////////////////////////////////////////////////////////////////
  //SENDERS calls https://mandrillapp.com/api/docs/senders.JSON.html
  //////////////////////////////////////////////////////////////////

  /**
    * Return the senders that have tried to use this account.
    *
    * @return the senders that have tried to use this account.
    */
  def sendersList: Future[Try[List[MSendersListResp]]] = {
    executeQuery[MVoid, List[MSendersListResp]](senderslist, MVoid())
  }

  /**
    * Returns the sender domains that have been added to this account.
    *
    * @return the sender domains that have been added to this account.
    */
  def sendersDomains(): Future[Try[List[MSendersDomainResponses]]] = {
    executeQuery[MVoid, List[MSendersDomainResponses]](sendersdomains, MVoid())
  }

  /**
    * Adds a sender domain to your account. Sender domains are added automatically as you send,
    * but you can use this call to add them ahead of time.
    *
    * @param snd - the domain to add
    * @return information about the domain
    */
  def sendersAddDomain(snd: MSenderDomain): Future[Try[MSendersDomainResponses]] = {
    executeQuery[MSenderDomain, MSendersDomainResponses](sendersadddom, snd)
  }


  /**
    * Checks the SPF and DKIM settings for a domain.
    * If you haven't already added this domain to your account, it will be added automatically.
    *
    * @param snd - the domain to add
    * @return information about the domain
    */
  def sendersCheckDomain(snd: MSenderDomain): Future[Try[MSendersDomainResponses]] = {
    executeQuery[MSenderDomain,MSendersDomainResponses](senderschkdom, snd)
  }

  /**
    * Sends a verification email in order to verify ownership of a domain. Domain verification is an optional step to
    * confirm ownership of a domain. Once a domain has been verified in a Mandrill account, other accounts may not have
    * their messages signed by that domain unless they also verify the domain.
    * This prevents other Mandrill accounts from sending mail signed by your domain.
    *
    * @param snd - the verification email to send
    * @return information about the verification that was sent
    */
  def sendersVerifyDomain(snd: MSenderVerifyDomain): Future[Try[MSendersVerifyDomResp]] = {
    executeQuery[MSenderVerifyDomain,MSendersVerifyDomResp](sendersverdom, snd)
  }

  /**
    * Return more detailed information about a single sender, including aggregates of recent stats
    *
    * @param snd - the email address of the sender
    * @return the detailed information on the sender
    */
  def sendersInfo(snd: MSenderAddress): Future[Try[MSendersInfoResp]] = {
    executeQuery[MSenderAddress,MSendersInfoResp](sendersinfo, snd)
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a sender
    *
    * @param snd - the email address of the sender
    * @return the array of history information
    */
  def sendersTimeSeries(snd: MSenderAddress): Future[Try[List[MSenderTSResponse]]] = {
    executeQuery[MSenderAddress,List[MSenderTSResponse]](sendersts, snd)
  }

  ////////////////////////////////////////////////////////////
  //URLS calls https://mandrillapp.com/api/docs/urls.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Get the 100 most clicked URLs
    *
    * @return the 100 most clicked URLs
    */
  def urlsList(): Future[Try[List[MUrlResponse]]] = {
    executeQuery[MVoid, List[MUrlResponse]](urllist, MVoid())
  }

  /**
    * Return the 100 most clicked URLs that match the search query given
    *
    * @param url - a search query
    * @return the 100 most clicked URLs that match the search query given
    */
  def urlsSearch(url: MUrlSearch): Future[Try[List[MUrlResponse]]] = {
    executeQuery[MUrlSearch,List[MUrlResponse]](urlsearch, url)
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a url
    *
    * @param url - a search query
    * @return the recent history (hourly stats for the last 30 days) for a url
    */
  def urlsTimeSeries(url: MUrlTimeSeries): Future[Try[List[MUrlTimeResponse]]] = {
    executeQuery[MUrlTimeSeries,List[MUrlTimeResponse]](urlts, url)
  }

  /**
    * Get the list of tracking domains set up for this account
    *
    * @return the list of tracking domains set up for this account
    */
  def urlsTrackingDomain(): Future[Try[List[MUrlDomainResponse]]] = {
    executeQuery[MVoid,List[MUrlDomainResponse]](urltrackdom, MVoid())
  }

  /**
    * Checks the CNAME settings for a tracking domain. The domain must have been added already with the add-tracking-domain call
    *
    * @param url - an existing tracking domain name
    * @return information about the tracking domain
    */
  def urlsCheckTrackingDomain(url: MUrlDomain): Future[Try[MUrlDomainResponse]] = {
    executeQuery[MUrlDomain,MUrlDomainResponse](urlchktrackdom, url)
  }

  /**
    * Add a tracking domain to your account
    *
    * @param url - a domain
    * @return information about the domain
    */
  def urlsAddTrackingDomain(url: MUrlDomain): Future[Try[MUrlDomainResponse]] = {
    executeQuery[MUrlDomain,MUrlDomainResponse](urladdtrackdom, url)
  }

  /////////////////////////////////////////////////////////////////////
  //TEMPLATE calls https://mandrillapp.com/api/docs/templates.JSON.html
  /////////////////////////////////////////////////////////////////////

  /**
    * Add a new template
    *
    * @param template - the template
    * @return the information saved about the new template
    */
  def templateAdd(template: MTemplate): Future[Try[MTemplateAddResponses]] = {
    executeQuery[MTemplate,MTemplateAddResponses](tmpadd, template)
  }

  /**
    * Get the information for an existing template
    *
    * @param template - the template
    * @return the requested template information
    */
  def templateInfo(template: MTemplateInfo): Future[Try[MTemplateAddResponses]] = {
    executeQuery[MTemplateInfo,MTemplateAddResponses](tmpinfo, template)
  }

  /**
    * Update the code for an existing template. If null is provided for any fields, the values will remain unchanged
    *
    * @param template - the template
    * @return the template that was updated
    */
  def templateUpdate(template: MTemplate): Future[Try[MTemplateAddResponses]] = {
    executeQuery[MTemplate,MTemplateAddResponses](tmpupdate, template)
  }

  /**
    * Publish the content for the template. Any new messages sent using this template will start using the content that was previously in draft.
    *
    * @param template - the template
    * @return the template that was published
    */
  def templatePublish(template: MTemplateInfo): Future[Try[MTemplateAddResponses]] = {
    executeQuery[MTemplateInfo,MTemplateAddResponses](tmppublish, template)
  }

  /**
    * Delete a template
    *
    * @param template - the template
    * @return the template that was deleted
    */
  def templateDelete(template: MTemplateInfo): Future[Try[MTemplateAddResponses]] = {
    executeQuery[MTemplateInfo,MTemplateAddResponses](tmpdelete, template)
  }

  /**
    * Return a list of all the templates available to this user
    *
    * @param template - the template
    * @return an array of objects with information about each template
    */
  def templateList(template: MTemplateList): Future[Try[List[MTemplateAddResponses]]] = {
    executeQuery[MTemplateList,List[MTemplateAddResponses]](tmplist, template)
  }

  /**
    * Return the recent history (hourly stats for the last 30 days) for a template
    *
    * @param template - the template
    * @return an array of history information
    */
  def templateTimeSeries(template: MTemplateInfo): Future[Try[List[MTimeSeriesResponse]]] = {
    executeQuery[MTemplateInfo,List[MTimeSeriesResponse]](tmpts, template)
  }

  /**
    * Inject content and optionally merge fields into a template, returning the HTML that results
    *
    * @param template - the template
    * @return the result of rendering the given template with the content and merge field values injected
    */
  def templateRender(template: MTemplateRender): Future[Try[MTemplateRenderResponse]] = {
    executeQuery[MTemplateRender,MTemplateRenderResponse](tmprender, template)
  }

  ////////////////////////////////////////////////////////////////////
  //WEBHOOKS calls https://mandrillapp.com/api/docs/webhooks.JSON.html
  ////////////////////////////////////////////////////////////////////

  /**
    * Get the list of all webhooks defined on the account
    *
    * @return the webhooks associated with the account
    */
  def webhookList(): Future[Try[List[MWebhooksResponse]]] = {
    executeQuery[MVoid,List[MWebhooksResponse]](webhlist, MVoid())
  }

  /**
    * Add a new webhook
    *
    * @param webhook
    * @return the information saved about the new webhook
    */
  def webhookAdd(webhook: MWebhook): Future[Try[MWebhooksResponse]] = {
    executeQuery[MWebhook,MWebhooksResponse](webhadd, webhook)
  }

  /**
    * Given the ID of an existing webhook, return the data about it
    *
    * @param webhook - the existing webhook
    * @return the information saved about the new webhook
    */
  def webhookInfo(webhook: MWebhookInfo): Future[Try[MWebhooksResponse]] = {
    executeQuery[MWebhookInfo,MWebhooksResponse](webhinfo, webhook)
  }

  /**
    * Update an existing webhook
    *
    * @param webhook - the existing webhook to update
    * @return the information saved about the new webhook
    */
  def webhookUpdate(webhook: MWebhookUpdate): Future[Try[MWebhooksResponse]] = {
    executeQuery[MWebhookUpdate,MWebhooksResponse](webhupdate, webhook)
  }

  /**
    * Delete an existing webhook
    *
    * @param webhook - the webhook to delete
    * @return the information saved about the new webhook
    */
  def webhookDelete(webhook: MWebhookInfo): Future[Try[MWebhooksResponse]] = {
    executeQuery[MWebhookInfo,MWebhooksResponse](webhdelete, webhook)
  }

  //////////////////////////////////////////////////////////////////////////
  //SUBACCOUNTS calls https://mandrillapp.com/api/docs/subaccounts.JSON.html
  //////////////////////////////////////////////////////////////////////////

  /**
    * Get the list of subaccounts defined for the account, optionally filtered by a prefix
    *
    * @param subacc - the prefix
    * @return the subaccounts for the account, up to a maximum of 1,000
    */
  def subaccountList(subacc: MSubaccountList): Future[Try[List[MSubaccountsResponse]]] = {
    executeQuery[MSubaccountList,List[MSubaccountsResponse]](sublist, subacc)
  }

  /**
    * Add a new subaccount
    *
    * @param subacc - the subaccount to add
    * @return the information saved about the new subaccount
    */
  def subaccountAdd(subacc: MSubaccount): Future[Try[MSubaccountsResponse]] = {
    executeQuery[MSubaccount,MSubaccountsResponse](subadd, subacc)
  }

  /**
    * Given the ID of an existing subaccount, return the data about it
    *
    * @param subacc - the existing subaccount
    * @return the information about the subaccount
    */
  def subaccountInfo(subacc: MSubaccountInfo): Future[Try[MSubaccountsInfoResponse]] = {
    executeQuery[MSubaccountInfo,MSubaccountsInfoResponse](subinfo, subacc)
  }

  /**
    * Update an existing subaccount
    *
    * @param subacc - the existing subaccount to update
    * @return the information for the updated subaccount
    */
  def subaccountUpdate(subacc: MSubaccount): Future[Try[MSubaccountsResponse]] = {
    executeQuery[MSubaccount,MSubaccountsResponse](subupdate, subacc)
  }

  /**
    * Delete an existing subaccount. Any email related to the subaccount will be saved, but stats will be removed and any future sending calls to this subaccount will fail.
    *
    * @param subacc - the subaccount to delete
    * @return the information for the deleted subaccount
    */
  def subaccountDelete(subacc: MSubaccountInfo): Future[Try[MSubaccountsResponse]] = {
    executeQuery[MSubaccountInfo,MSubaccountsResponse](subdelete, subacc)
  }

  /**
    * Pause a subaccount's sending. Any future emails delivered to this subaccount will be queued for a maximum of 3 days until the subaccount is resumed.
    *
    * @param subacc - the subaccount to pause
    * @return the information for the paused subaccount
    */
  def subaccountPause(subacc: MSubaccountInfo): Future[Try[MSubaccountsResponse]] = {
    executeQuery[MSubaccountInfo,MSubaccountsResponse](subpause, subacc)
  }

  /**
    * Resume a paused subaccount's sending
    *
    * @param subacc - the subaccount to resume
    * @return the information for the resumed subaccount
    */
  def subaccountResume(subacc: MSubaccountInfo): Future[Try[MSubaccountsResponse]] = {
    executeQuery[MSubaccountInfo,MSubaccountsResponse](subresume, subacc)
  }

  ////////////////////////////////////////////////////////////
  //INBOUND https://mandrillapp.com/api/docs/key.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * List the domains that have been configured for inbound delivery
    *
    * @return the inbound domains associated with the account
    */
  def inboundDomains(): Future[Try[List[MInboundDomainResponse]]] = {
    executeQuery[MVoid,List[MInboundDomainResponse]](inbdom, MVoid())
  }

  /**
    * Add an inbound domain to your account
    *
    * @param inbound - the domain to add
    * @return information about the domain
    */
  def inboundAddDomain(inbound: MInboundDomain): Future[Try[MInboundDomainResponse]] = {
    executeQuery[MInboundDomain,MInboundDomainResponse](inbadddom, inbound)
  }

  /**
    * Check the MX settings for an inbound domain. The domain must have already been added with the add-domain call
    *
    * @param inbound - the domain to check
    * @return information about the inbound domain
    */
  def inboundCheckDomain(inbound: MInboundDomain): Future[Try[MInboundDomainResponse]] = {
    executeQuery[MInboundDomain,MInboundDomainResponse](inbchkdom, inbound)
  }

  /**
    * Delete an inbound domain from the account. All mail will stop routing for this domain immediately.
    *
    * @param inbound - the domain to delete
    * @return information about the inbound domain
    */
  def inboundDeleteDomain(inbound: MInboundDomain): Future[Try[MInboundDomainResponse]] = {
    executeQuery[MInboundDomain,MInboundDomainResponse](inbdeldom, inbound)
  }

  /**
    * List the mailbox routes defined for an inbound domain
    *
    * @param inbound - the domain
    * @return the routes associated with the domain
    */
  def inboundRoutes(inbound: MInboundDomain): Future[Try[List[MInboundRouteResponse]]] = {
    executeQuery[MInboundDomain,List[MInboundRouteResponse]](inbroutes, inbound)
  }

  /**
    * Add a new mailbox route to an inbound domain
    *
    * @param inbound - the domain
    * @return the added mailbox route information
    */
  def inboundAddRoute(inbound: MInboundRoute): Future[Try[MInboundRouteResponse]] = {
    executeQuery[MInboundRoute,MInboundRouteResponse](inbaddroute, inbound)
  }

  /**
    * Update the pattern or webhook of an existing inbound mailbox route. If null is provided for any fields, the values will remain unchanged.
    *
    * @param inbound - the route to update
    * @return the updated mailbox route information
    */
  def inboundUpdateRoute(inbound: MInboundUpdateRoute): Future[Try[MInboundRouteResponse]] = {
    executeQuery[MInboundUpdateRoute,MInboundRouteResponse](inbuproute, inbound)
  }

  /**
    * Delete an existing inbound mailbox route
    *
    * @param inbound - the route to delete
    * @return the deleted mailbox route information
    */
  def inboundDeleteRoute(inbound: MInboundDelRoute): Future[Try[MInboundRouteResponse]] = {
    executeQuery[MInboundDelRoute,MInboundRouteResponse](inbdelroute, inbound)
  }

  /**
    * Take a raw MIME document destined for a domain with inbound domains set up, and send it to the inbound hook exactly as if it had been sent over SMTP
    *
    * @param inbound - raw MIME document
    * @return an array of the information for each recipient in the message (usually one) that matched an inbound route
    */
  def inboundSendRaw(inbound: MInboundRaw): Future[Try[List[MInboundRawResponse]]] = {
    executeQuery[MInboundRaw,List[MInboundRawResponse]](inbraw, inbound)
  }

  ////////////////////////////////////////////////////////////
  //EXPORT https://mandrillapp.com/api/docs/exports.JSON.html
  ////////////////////////////////////////////////////////////

  /**
    * Returns information about an export job. If the export job's state is 'complete', the returned data will include
    * a URL you can use to fetch the results. Every export job produces a zip archive, but the format of the archive
    * is distinct for each job type.
    * The api calls that initiate exports include more details about the output format for that job type.
    *
    * @param export - the export type
    * @return the information about the export
    */
  def exportInfo(export: MExportInfo): Future[Try[MExportResponse]] = {
    executeQuery[MExportInfo,MExportResponse](expinfo, export)
  }

  /**
    * Returns a list of your exports.
    *
    * @return the account's exports
    */
  def exportList(): Future[Try[List[MExportResponse]]] = {
    executeQuery[MVoid,List[MExportResponse]](explist, MVoid())
  }

  /**
    * Begins an export of your rejection blacklist. The blacklist will be exported to a zip archive containing a single
    * file named rejects.csv that includes the following fields: email, reason, detail, created_at, expires_at, last_event_at, expires_at.
    *
    * @param export - the export job
    * @return information about the rejects export job that was started
    */
  def exportReject(export: MExportNotify): Future[Try[MExportResponse]] = {
    executeQuery[MExportNotify,MExportResponse](exprec, export)
  }

  /**
    * Begins an export of your rejection whitelist. The whitelist will be exported to a zip archive containing a single
    * file named whitelist.csv that includes the following fields: email, detail, created_at.
    *
    * @param export - the export job
    * @return information about the whitelist export job that was started
    */
  def exportWhitelist(export: MExportNotify): Future[Try[MExportResponse]] = {
    executeQuery[MExportNotify,MExportResponse](expwhite, export)
  }

  /**
    * Begins an export of your activity history. The activity will be exported to a zip archive containing a single file
    * named activity.csv in the same format as you would be able to export from your account's activity view.
    * It includes the following fields: Date, Email Address, Sender, Subject, Status, Tags, Opens, Clicks, Bounce Detail.
    * If you have configured any custom metadata fields, they will be included in the exported data.
    *
    * @param export - the export activity
    * @return information about the activity export job that was started
    */
  def exportActivity(export: MExportActivity): Future[Try[MExportResponse]] = {
    executeQuery[MExportActivity,MExportResponse](expactivity, export)
  }

  ////////////////////////////////////////////////////
  //ISP https://mandrillapp.com/api/docs/ips.JSON.html
  ////////////////////////////////////////////////////

  /**
    * Lists your dedicated IPs.
    *
    * @return an array of structs for each dedicated IP
    */
  def ipsList(): Future[Try[List[MIpsResponse]]] = {
    executeQuery[MVoid,List[MIpsResponse]](ipslist, MVoid())
  }

  /**
    * Retrieves information about a single dedicated ip.
    *
    * @param ip - the ip
    * @return Information about the dedicated ip
    */
  def ipsInfo(ip: MIpsIp): Future[Try[MIpsResponse]] = {
    executeQuery[MIpsIp,MIpsResponse](ipsinfo, ip)
  }

  /**
    * Requests an additional dedicated IP for your account. Accounts may have one outstanding request at any time,
    * and provisioning requests are processed within 24 hours.
    *
    * @param ip - the isp
    * @return a description of the provisioning request that was created
    */
  def ipsProvision(ip: MIpsPool): Future[Try[MIpsProvisionResp]] = {
    executeQuery[MIpsPool,MIpsProvisionResp](ipsprov, ip)
  }

  /**
    * Begins the warmup process for a dedicated IP. During the warmup process, Mandrill will gradually increase the
    * percentage of your mail that is sent over the warming-up IP, over a period of roughly 30 days. The rest of your
    * mail will be sent over shared IPs or other dedicated IPs in the same pool.
    *
    * @param isp - the isp
    * @return Information about the dedicated IP
    */
  def ipsStartWarmup(isp: MIpsIp): Future[Try[MIpsResponse]] = {
    executeQuery[MIpsIp,MIpsResponse](ipsstwarm, isp)
  }

  /**
    * Cancels the warmup process for a dedicated IP.
    *
    * @param ip - the isp
    * @return Information about the dedicated IP
    */
  def ipsCancelWarmup(ip: MIpsIp): Future[Try[MIpsResponse]] = {
    executeQuery[MIpsIp,MIpsResponse](ipscancwarm, ip)
  }

  /**
    * Moves a dedicated IP to a different pool.
    *
    * @param pool - the ip pool
    * @return Information about the updated state of the dedicated IP
    */
  def ipsSetPool(pool: MIpsSetPool): Future[Try[MIpsResponse]] = {
    executeQuery[MIpsSetPool,MIpsResponse](ipssetpool, pool)
  }

  /**
    * Deletes a dedicated IP. This is permanent and cannot be undone.
    *
    * @param ip - the ip
    * @return a description of the ip that was removed from your account.
    */
  def ipsDelete(ip: MIpsIp): Future[Try[MIpsDelete]] = {
    executeQuery[MIpsIp,MIpsDelete](ipsdel, ip)
  }

  /**
    * Lists your dedicated IP pools.
    *
    * @return the dedicated IP pools for your account, up to a maximum of 1,000
    */
  def ipsListPool(): Future[Try[List[MIpsInfoPoolResponse]]] = {
    executeQuery[MVoid,List[MIpsInfoPoolResponse]](ipslistpool, MVoid())
  }

  /**
    * Describes a single dedicated IP pool.
    *
    * @param pool - the ip pool
    * @return Information about the dedicated ip pool
    */
  def ipsPoolInfo(pool: MIpsPoolInfo): Future[Try[MIpsInfoPoolResponse]] = {
    executeQuery[MIpsPoolInfo,MIpsInfoPoolResponse](ipspoolinfo, pool)
  }

  /**
    * Creates a pool and returns it. If a pool already exists with this name, no action will be performed.
    *
    * @param pool - the pool
    * @return Information about the dedicated ip pool
    */
  def ipsCreatePool(pool: MIpsPoolInfo): Future[Try[MIpsInfoPoolResponse]] = {
    executeQuery[MIpsPoolInfo,MIpsInfoPoolResponse](ipscreatep, pool)
  }

  /**
    * Deletes a pool. A pool must be empty before you can delete it, and you cannot delete your default pool.
    *
    * @param pool - the pool
    * @return information about the status of the pool that was deleted
    */
  def ipsDeletePool(pool: MIpsPoolInfo): Future[Try[MIpsDeletePoolResponse]] = {
    executeQuery[MIpsPoolInfo,MIpsDeletePoolResponse](ipsdeletep, pool)
  }

  /**
    * Configures the custom DNS name for a dedicated IP.
    *
    * @param dns - custom dns
    * @return information about the dedicated IP's new configuration
    */
  def ipsSetCustomDns(dns: MIpsDns): Future[Try[MIpsResponse]] = {
    executeQuery[MIpsDns,MIpsResponse](ipsetdns, dns)
  }

  /**
    * Checks the status of custom DNS on a dedicated IP.
    *
    * @param dns - custom dns
    * @return information about the dedicated IP's new configuration
    */
  def ipsCheckCustomDns(dns: MIpsDns): Future[Try[MIpsDnsResponse]] = {
    executeQuery[MIpsDns,MIpsDnsResponse](ipchkdns, dns)
  }

  //////////////////////////////////////////////////////////////
  //METADATA https://mandrillapp.com/api/docs/metadata.JSON.html
  //////////////////////////////////////////////////////////////

  /**
    * Get the list of custom metadata fields indexed for the account.
    *
    * @return the custom metadata fields for the account
    */
  def metadataList(): Future[Try[List[MIMetadataResponse]]] = {
    executeQuery[MVoid,List[MIMetadataResponse]](metalist, MVoid())
  }

  /**
    * Add a new custom metadata field to be indexed for the account.
    *
    * @param meta - the metadata to add
    * @return the information saved about the new metadata field
    */
  def metadataAdd(meta: MMeteadatapAdd): Future[Try[MIMetadataResponse]] = {
    executeQuery[MMeteadatapAdd,MIMetadataResponse](metaadd, meta)
  }

  /**
    * Update an existing custom metadata field.
    *
    * @param meta - the metadata to update
    * @return the information for the updated metadata field
    */
  def metadataUpdate(meta: MMeteadatapAdd): Future[Try[MIMetadataResponse]] = {
    executeQuery[MMeteadatapAdd,MIMetadataResponse](metaupdate, meta)
  }

  /**
    * Delete an existing custom metadata field. Deletion isn't instataneous, and /metadata/list will continue to return
    * the field until the asynchronous deletion process is complete.
    *
    * @param meta - the metadata to delete
    * @return the information for the deleted metadata field
    */
  def metadataDelete(meta: MMeteadatapDelete): Future[Try[MIMetadataResponse]] = {
    executeQuery[MMeteadatapDelete,MIMetadataResponse](metadel, meta)
  }

}

object MandrillClient {
  /**
    * An enumeration of the endpoints for Mandrill API
    *
    * @see https://mandrillapp.com/api/docs/
    */
  object Endpoints extends Enumeration {
    private[MandrillClient] final def Value(name: String, endpoint: String): Endpoint = new Endpoint(endpoint)

    class Endpoint(val endpoint: String) extends Val(nextId, endpoint)

    //users
    val ping = Value("ping", "/users/ping.json")
    val ping2 = Value("ping2", "/users/ping2.json")
    val senders = Value("senders", "/users/senders.json")
    val info = Value("info", "/users/info.json")
    //messages
    val send = Value("send", "/messages/send.json")
    val sendTemplate = Value("sendtemplate", "/messages/send-template.json")
    val search = Value("search", "/messages/search.json")
    val searchTimeS = Value("searchts", "/messages/search-time-series.json")
    val msginfo = Value("msginfo", "/messages/info.json")
    val content = Value("content", "/messages/content.json")
    val parse = Value("parse", "/messages/parse.json")
    val sendraw = Value("sendraw", "/messages/send-raw.json")
    val listSchedule = Value("sclist", "/messages/list-scheduled.json")
    val cancelSchedule = Value("sccanc", "/messages/cancel-scheduled.json")
    val reschedule = Value("scre", "/messages/reschedule.json")
    //tags
    val taglist = Value("taglist", "/tags/list.json")
    val tagdelete = Value("tagdelete", "/tags/delete.json")
    val taginfo = Value("taginfo", "/tags/info.json")
    val tagtimeseries = Value("tagts", "/tags/time-series.json")
    val tagalltime = Value("tagallts", "/tags/all-time-series.json")
    //rejects
    val rejadd = Value("rejadd", "/rejects/add.json")
    val rejlist = Value("rejlist", "/rejects/list.json")
    val rejdelete = Value("rejdelete", "/rejects/delete.json")
    //whitelist
    val wlistadd = Value("wlistadd", "/whitelists/add.json")
    val wlistdelete = Value("wlistdelete", "/whitelists/delete.json")
    val wlistlist = Value("wlistlist", "/whitelists/list.json")
    //senders
    val senderslist = Value("senderslist", "/senders/list.json")
    val sendersdomains = Value("sendersdom", "/senders/domains.json")
    val sendersadddom = Value("sendersadddom", "/senders/add-domain.json")
    val senderschkdom = Value("senderscheck", "/senders/check-domain.json")
    val sendersverdom = Value("sendersverdom", "/senders/verify-domain.json")
    val sendersinfo = Value("sendersinfo", "/senders/info.json")
    val sendersts = Value("sendersts", "/senders/time-series.json")
    //key
    val urllist = Value("urllist", "/urls/list.json")
    val urlsearch = Value("urlsearch", "/urls/search.json")
    val urlts = Value("urlts", "/urls/time-series.json")
    val urltrackdom = Value("urltrackdom", "/urls/tracking-domains.json")
    val urladdtrackdom = Value("urladdtrackdm", "/urls/add-tracking-domain.json")
    val urlchktrackdom = Value("urlchktrackdm", "/urls/check-tracking-domain.json")
    //template
    val tmpadd = Value("tmpadd", "/templates/add.json")
    val tmpinfo = Value("tmpinfo", "/templates/info.json")
    val tmpupdate = Value("tmpupdate", "/templates/update.json")
    val tmppublish = Value("tmppublish", "/templates/publish.json")
    val tmpdelete = Value("tmpdelete", "/templates/delete.json")
    val tmplist = Value("tmplist", "/templates/list.json")
    val tmpts = Value("tmpts", "/templates/time-series.json")
    val tmprender = Value("tmprender", "/templates/render.json")
    //webhooks
    val webhlist = Value("webhlist", "/webhooks/list.json")
    val webhadd = Value("webhadd", "/webhooks/add.json")
    val webhinfo = Value("webhinfo", "/webhooks/info.json")
    val webhupdate = Value("webhupdate", "/webhooks/update.json")
    val webhdelete = Value("webhdelete", "/webhooks/delete.json")
    //subaccounts
    val sublist = Value("sublist", "/subaccounts/list.json")
    val subadd = Value("subadd", "/subaccounts/add.json")
    val subinfo = Value("subinfo", "/subaccounts/info.json")
    val subupdate = Value("subupdte", "/subaccounts/update.json")
    val subdelete = Value("subdelete", "/subaccounts/delete.json")
    val subpause = Value("subpause", "/subaccounts/pause.json")
    val subresume = Value("subresumt", "/subaccounts/resume.json")
    //key
    val inbdom = Value("inbdom", "/inbound/domains.json")
    val inbadddom = Value("inbadddom", "/inbound/add-domain.json")
    val inbchkdom = Value("inbchkdom", "/inbound/check-domain.json")
    val inbdeldom = Value("inbdeldom", "/inbound/delete-domain.json")
    val inbroutes = Value("inbroutes", "/inbound/routes.json")
    val inbaddroute = Value("inbaddroute", "/inbound/add-route.json")
    val inbuproute = Value("inbuproute", "/inbound/update-route.json")
    val inbdelroute = Value("inbdelroute", "/inbound/delete-route.json")
    val inbraw = Value("inbraw", "/inbound/send-raw.json")
    //key
    val expinfo = Value("expinfo", "/exports/info.json")
    val explist = Value("explist", "/exports/list.json")
    val exprec = Value("exprej", "/exports/rejects.json")
    val expwhite = Value("expwhite", "/exports/whitelist.json")
    val expactivity = Value("expact", "/exports/activity.json")
    //key
    val ipslist = Value("ipslist", "/ips/list.json")
    val ipsinfo = Value("ipsinfo", "/ips/info.json")
    val ipsprov = Value("ipsprov", "/ips/provision.json")
    val ipsstwarm = Value("ipslistw", "/ips/start-warmup.json")
    val ipscancwarm = Value("ispcancw", "/ips/cancel-warmup.json")
    val ipssetpool = Value("ipssetpool", "/ips/set-pool.json")
    val ipsdel = Value("ipsdel", "/ips/delete.json")
    val ipslistpool = Value("ipslistpool", "/ips/list-pools.json")
    val ipspoolinfo = Value("ipspoolinfo", "/ips/pool-info.json")
    val ipscreatep = Value("ipscreatep", "/ips/create-pool.json")
    val ipsdeletep = Value("ipsdelpool", "/ips/delete-pool.json")
    val ipsetdns = Value("ipsetdns", "/ips/set-custom-dns.json")
    val ipchkdns = Value("ipchkdns", "/ips/check-custom-dns.json")
    //metadata
    val metalist = Value("metalist", "/metadata/list.json")
    val metaadd = Value("metaadd", "/metadata/add.json")
    val metaupdate = Value("metaupdate", "/metadata/update.json")
    val metadel = Value("metadel", "/metadata/delete.json")
  }
}

object implicits {
  import scala.language.implicitConversions
  implicit def stringEntryToJsScalarEntry(e: (String, String)) = MMetadataEntry(e._1, JsScalarString(e._2))
  implicit def booleanEntryToJsScalarEntry(e: (String, Boolean)) = MMetadataEntry(e._1, JsScalarBoolean(e._2))
  implicit def intEntryToJsScalarEntry(e: (String, Int)) = MMetadataEntry(e._1, JsScalarNumber(e._2))
  implicit def bigDecimalEntryToJsScalarEntry(e: (String, BigDecimal)) = MMetadataEntry(e._1, JsScalarNumber(e._2))

  implicit class MakeOptional[T <: Optional[T]](o: T) { def ? : Option[T] = Some(o) }
  implicit class OptionalString(s: String) { def ? : Option[String] = Some(s) }
  implicit class OptionalBoolean(b: Boolean) { def ? : Option[Boolean] = Some(b) }
  implicit class OptionalList[T](l: List[T]) { def ? : Option[List[T]] = Some(l) }
}
